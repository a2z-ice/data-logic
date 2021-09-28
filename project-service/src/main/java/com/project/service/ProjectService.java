package com.project.service;

import com.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProjectService {
    List<Project> getAllProjects();
    Page<Project> getAllProjectsPageable(Pageable pageable );
    Project save(Project project);

    Project update(Long id, Project project);

    void delete(Long id);


    Project publish(Long id);
}
