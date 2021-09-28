package com.project.service;

import com.project.config.AppConfig;
import com.project.entity.Project;
import com.project.entity.Section;
import com.project.entity.Status;
import com.project.error.ApplicationExcption;
import com.project.error.ErrorUtils;
import com.project.model.PublishSearchDataPayload;
import com.project.repo.ProjectRepository;
import com.project.repo.SectionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Log
public class ProjectServiceImpl implements ProjectService {

    public static final String PUBLISH_TO_SEARCH_URI = "_publish";
    private final ProjectRepository projectRepository;
    private final SectionRepository sectionRepository;
    private final WebClient.Builder webClientBuilder;
    private final AppConfig appConfig;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    @Override
    public Page<Project> getAllProjectsPageable(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project update(Long id, Project project) {
        project.setId(id);
        Project existingProject = projectRepository.findById(id).orElseThrow(() -> ErrorUtils.throwEntityNotFoundException(id));
        BeanUtils.copyProperties(project, existingProject);
        return projectRepository.saveAndFlush(project);
    }

    @Override
    public void delete(Long id) {
        projectRepository.findById(id).orElseThrow(() -> ErrorUtils.throwEntityNotFoundException(id));
        projectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Project publish(Long projectId) {
        Project existingProject = getValidatedExistingProjectById(projectId);
        createProjectSnapshot(existingProject);
        pushToElasticSearch(existingProject);
        return existingProject;
    }
    private Project getValidatedExistingProjectById(Long projectId){
        Project existingProject = projectRepository
                .findByProjectIdWithSections(projectId)
                .orElseThrow(() -> ErrorUtils.throwEntityNotFoundException(projectId))
                ;
        if(Status.PUBLISHED == existingProject.getStatus())
            throw new ApplicationExcption("Project id:" + projectId + " already published");
        return existingProject;
    }
    private void createProjectSnapshot(Project projectForPublish){
        projectForPublish.setStatus(Status.PUBLISHED);
        projectRepository.save(projectForPublish);
        createNewDraftedProjectFromPublishedProject(projectForPublish);

    }

    private Project createNewDraftedProjectFromPublishedProject(Project publishedProject){
        Project newDraftProject = new Project();
        BeanUtils.copyProperties(publishedProject,newDraftProject);
        sanitizeNewDraftedProject(newDraftProject);
        newDraftProject.setStatus(Status.DRAFT);
        projectRepository.save(newDraftProject);
        addNewProjectToSections(newDraftProject,publishedProject.getSections());

        return publishedProject;
    }

    private void sanitizeNewDraftedProject(Project newDraftProject){
        newDraftProject.setId(null);
        newDraftProject.setSections(null);
    }

    private void addNewProjectToSections(final Project newDraftProject, List<Section> sections){
        if(sections == null || sections.isEmpty()) Collections.emptyList();

        sectionRepository.saveAll(sections.stream()
                .map(selection -> this.createNewSection(newDraftProject.getId(), selection))
                .collect(Collectors.toList()));
        ;


    }

    private Section createNewSection(Long projectId ,Section existingSection){
        Section newSection = new Section();
        BeanUtils.copyProperties(existingSection, newSection);
        newSection.setProjectId(projectId);
        newSection.setId(null);
        return newSection;
    }



    public void pushToElasticSearch(Project publishedProject){
        try {
            webClientBuilder.build()
                    .post().uri(appConfig.getOnesearchSvcUrl()+ PUBLISH_TO_SEARCH_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(projectToPublishDataPayload(publishedProject)),PublishSearchDataPayload.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception ex){
            log.log(Level.WARNING,ex.getMessage(),ex);
        }

    }

    private PublishSearchDataPayload projectToPublishDataPayload(Project project){
        PublishSearchDataPayload data = new PublishSearchDataPayload();
        data.setId("Project_"+ project.getId());
        StringBuilder sb = new StringBuilder();
        sb.append(getEmptyIfNull(project.getTitle()));
        sb.append(getEmptyIfNull(project.getDescription()));
        String sectionsString = project.getSections().stream().map(section -> new StringBuilder(getEmptyIfNull(section.getTitle()) + " " + getEmptyIfNull(section.getDescription())))
                .collect(Collectors.joining(" "));
        sb.append(sectionsString);
        data.setContent(sb.toString());
        return data;
    }

    private String getEmptyIfNull(String value){
        return value == null? "" : value;
    }
}
