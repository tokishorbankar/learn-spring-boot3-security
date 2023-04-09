package com.kb.learn.api.controller;


import com.kb.learn.module.ApiResponse;
import com.kb.learn.module.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class RestAPIController {

    List<Student> students = List.of(
            new Student("name1", 1L),
            new Student("name2", 2L));

    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getStudents() {
        log.debug("Retrieving students");
        log.info("Retrieving students");
        return ResponseEntity.ok(new ApiResponse<>(students));
    }
}
