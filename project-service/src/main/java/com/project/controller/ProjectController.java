package com.project.controller;

import com.project.entity.Project;
import com.project.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Log
@RequestMapping("/projects")
public class ProjectController {

    ProjectService projectServcie;


    @GetMapping("/{id}/_publish")
    public Project publishProject(@PathVariable Long id){
        return projectServcie.publish(id);
    }

    @GetMapping("/_all")
    public ResponseEntity<Map<String, Object>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable paging = PageRequest.of(page, size);
        Page<Project> allProjectsPageable = projectServcie.getAllProjectsPageable(paging);
        List<Project> projectList = allProjectsPageable.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("projects", projectList);
        response.put("currentPage", allProjectsPageable.getNumber());
        response.put("totalItems", allProjectsPageable.getTotalElements());
        response.put("totalPages", allProjectsPageable.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/_create")
    public ResponseEntity<Project> createProject(@RequestBody  Project project){
        projectServcie.save(project);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @PutMapping("{id}/_update")
    public Project updateProject(@PathVariable("id") Long id,@RequestBody  Project project){

        return projectServcie.update(id, project);
    }

    @DeleteMapping("{id}/_delete")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("id") Long id){
        projectServcie.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
