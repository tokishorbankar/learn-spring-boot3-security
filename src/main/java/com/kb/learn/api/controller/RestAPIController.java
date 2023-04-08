package com.kb.learn.api.controller;


import com.kb.learn.api.module.ApiResponseEntity;
import com.kb.learn.api.module.Student;
import lombok.RequiredArgsConstructor;
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
public class RestAPIController {

    List<Student> students = List.of(
            new Student("name1", 1l),
            new Student("name2", 2l));

    @GetMapping
    public ResponseEntity<ApiResponseEntity> getStudents() {

        return ResponseEntity.ok(ApiResponseEntity.builder().body(students).build());
    }
}
