package com.project.service;

import com.project.entity.Section;
import com.project.error.ErrorUtils;
import com.project.repo.SectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SectionServiceImpl implements SectionService{
    private final SectionRepository sectionRepository;
    @Override
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    @Override
    public Section save(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    public Section update(Long id, Section section) {
        section.setId(id);
        Section existingSection = sectionRepository.findById(id).orElseThrow(() -> ErrorUtils.throwEntityNotFoundException(id));
        BeanUtils.copyProperties(section, existingSection);
        return sectionRepository.saveAndFlush(section);
    }

    @Override
    public void delete(Long id) {
        sectionRepository.findById(id).orElseThrow(() -> ErrorUtils.throwEntityNotFoundException(id));
        sectionRepository.deleteById(id);
    }
}
