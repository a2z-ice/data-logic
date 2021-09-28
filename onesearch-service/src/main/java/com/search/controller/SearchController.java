package com.search.controller;

import com.search.entity.PublishDataPayload;
import com.search.repo.PublishDataPayloadRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Log
@RequestMapping("/")
public class SearchController {

    PublishDataPayloadRepository publishDataPayloadRepository;

    @GetMapping("/_search")
    public List<PublishDataPayload> searchProject(@RequestParam(required = true) String query){
        return publishDataPayloadRepository.findByNameLikeUsingNamedParameter(query);
    }

    @PostMapping("/_publish")
    public ResponseEntity<HttpStatus> acceptPublishedProject(@RequestBody PublishDataPayload publishDataPayload){
        publishDataPayloadRepository.save(publishDataPayload);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
