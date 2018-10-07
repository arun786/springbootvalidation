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
