package com.project.service;

import com.project.entity.Project;
import com.project.entity.Section;

import java.util.List;

public interface SectionService {

    List<Section> getAllSections();
    Section save(Section section);

    Section update(Long id, Section section);

    void delete(Long id);




}
