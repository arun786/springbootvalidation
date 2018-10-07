package com.arun.springbootvalidation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error {
    private String code;
    private String description;
}
