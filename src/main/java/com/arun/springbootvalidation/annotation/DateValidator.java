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
