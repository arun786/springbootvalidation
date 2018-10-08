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
