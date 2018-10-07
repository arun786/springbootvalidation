# springbootvalidation


To use the validation @valid, we need to follow the below steps

    1. Define different types of validations in model, which will be the request body in controller
    2. The request body will be anotated as @valid
    3. we need to define the exception class

use of @valid

# Model

    package com.arun.springbootvalidation.model;
    
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    
    import javax.validation.constraints.FutureOrPresent;
    import javax.validation.constraints.NotEmpty;
    import javax.validation.constraints.PastOrPresent;
    import java.time.LocalDate;
    
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Student {
        private String studentId;
        @NotEmpty(message = "Name cannot be empty")
        private String name;
        private String age;
        @PastOrPresent(message = "from Date cannot be future date")
        private LocalDate fromDate;
        @FutureOrPresent(message = "to date cannot be past date")
        private LocalDate toDate;
    }

# Controller

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
    
    @RestController
    public class StudentController {
    
        private StudentService studentService;
    
        @Autowired
        public StudentController(StudentService studentService) {
            this.studentService = studentService;
        }
    
    
        @PostMapping("/students/v1/student")
        public ResponseEntity<Student> getStudent(@Valid @RequestBody Student student) {
            Student response = studentService.createStudent(student);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
 
# Exception 

    package com.arun.springbootvalidation.exception;
    
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.FieldError;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.RestController;
    import org.springframework.web.context.request.WebRequest;
    import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
    
    @ControllerAdvice
    @RestController
    public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    
    
        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            FieldError fieldError = ex.getBindingResult().getFieldError();
            Error error = new Error(HttpStatus.BAD_REQUEST.toString(), fieldError != null ? fieldError.getDefaultMessage() : ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
