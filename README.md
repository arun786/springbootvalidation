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


# Custom Validator

To check if the from date is greater than to date

    package com.arun.springbootvalidation.annotation;
    
    import org.apache.tomcat.jni.Local;
    
    import javax.validation.Constraint;
    import javax.validation.Payload;
    import java.lang.annotation.*;
    import java.time.LocalDate;
    
    @Constraint(validatedBy = DateValidator.class)
    @Documented
    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DateMatch {
    
        String message() default "To date is less than From date";
    
        Class<?>[] groups() default {};
    
        Class<? extends Payload>[] payload() default {};
    
        String fromDate();
    
        String toDate();
    
    
        @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @Documented
        @interface List {
            DateMatch[] value();
        }
    }
    

    package com.arun.springbootvalidation.annotation;
    
    
    import javax.validation.ConstraintValidator;
    import javax.validation.ConstraintValidatorContext;
    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    
    import static org.apache.commons.beanutils.PropertyUtils.getProperty;
    
    public class DateValidator implements ConstraintValidator<DateMatch, Object> {
    
        private String fromDate;
        private String toDate;
    
        @Override
        public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
    
            try {
                Object firstDate = getProperty(o, this.fromDate);
                Object toDate = getProperty(0, this.toDate);
    
                LocalDate localFromDate = LocalDate.parse(String.valueOf(firstDate), DateTimeFormatter.ofPattern("mm/dd/yyyy"));
                LocalDate localToDate = LocalDate.parse(String.valueOf(toDate), DateTimeFormatter.ofPattern("mm/dd/yyyy"));
                return localFromDate.isAfter(localToDate);
            } catch (Exception e) {
                return false;
            }
    
        }
    
        @Override
        public void initialize(DateMatch constraintAnnotation) {
            fromDate = constraintAnnotation.fromDate();
            toDate = constraintAnnotation.toDate();
    
        }
    }

    
    package com.arun.springbootvalidation.model;
    
    import com.arun.springbootvalidation.annotation.DateMatch;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    
    import javax.validation.constraints.NotEmpty;
    import javax.validation.constraints.PastOrPresent;
    import java.time.LocalDate;
    
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @DateMatch(fromDate = "fromDate", toDate = "toDate", message = "from date should not be greater than to date")
    public class Student {
        private String studentId;
        @NotEmpty(message = "Name cannot be empty")
        private String name;
        private String age;
        @PastOrPresent(message = "from Date cannot be future date")
        private LocalDate fromDate;
        @PastOrPresent(message = "to date cannot be past date")
        private LocalDate toDate;
    }

# exception

    package com.arun.springbootvalidation.exception;
    
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.FieldError;
    import org.springframework.validation.ObjectError;
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
            ObjectError globalError = ex.getBindingResult().getGlobalError();
            FieldError fieldError = ex.getBindingResult().getFieldError();
            Error error = new Error(String.valueOf(HttpStatus.BAD_REQUEST), fieldError != null ? fieldError.getDefaultMessage() : globalError.getDefaultMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
