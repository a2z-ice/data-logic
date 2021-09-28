package com.project.repo;

import com.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("Select p from Project p left join fetch p.sections s")
    List<Project> getAllProjects();

//    @Query("Select p from Project p left join fetch p.sections s")
//    Page<Project> findAll(Pageable pageable);

    @Query("Select p from Project p left join fetch p.sections s where p.id = :id")
    Optional<Project> findByProjectIdWithSections(Long id);

}