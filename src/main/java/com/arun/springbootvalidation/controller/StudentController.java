package com.arun.springbootvalidation.controller;

import com.arun.springbootvalidation.model.Student;
import com.arun.springbootvalidation.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/students/v1/student")
    public ResponseEntity<Student> getStudent(@Valid @RequestBody List<Student> student) {
        Student response = studentService.createStudent(student);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
