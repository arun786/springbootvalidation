package com.arun.springbootvalidation.service;

import com.arun.springbootvalidation.model.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(List<Student> student);
}
