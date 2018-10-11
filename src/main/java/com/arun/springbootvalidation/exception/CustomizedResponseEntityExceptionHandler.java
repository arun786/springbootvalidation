package com.arun.springbootvalidation.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        List<Error> errorList = new ArrayList<>();
        allErrors.forEach(error -> {
            Error errors = new Error(String.valueOf(HttpStatus.BAD_REQUEST), error.getDefaultMessage());
            errorList.add(errors);
        });

        return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
    }
}
