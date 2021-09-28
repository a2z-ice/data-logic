package com.project.controller;

import com.project.entity.Section;
import com.project.service.SectionService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Log
@RequestMapping("/sections")
public class SectionController {


   private final SectionService sectionService;

    @GetMapping("/_all")
    public List<Section> getAllSections(){
        return sectionService.getAllSections();
    }

    @PostMapping("/_create")
    public ResponseEntity<Section> createSection(@RequestBody  Section section){
        sectionService.save(section);
        return new ResponseEntity<>(section, HttpStatus.CREATED);
    }

    @PutMapping("{id}/_update")
    public Section updateSection(@PathVariable("id") Long id,@RequestBody  Section section){
        return sectionService.update(id, section);
    }

    @DeleteMapping("{id}/_delete")
    public ResponseEntity<HttpStatus> deleteSection(@PathVariable("id") Long id){
        sectionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
