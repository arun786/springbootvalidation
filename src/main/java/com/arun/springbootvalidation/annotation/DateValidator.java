package com.arun.springbootvalidation.annotation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static org.apache.commons.beanutils.PropertyUtils.getProperty;

public class DateValidator implements ConstraintValidator<DateMatch, Object> {

    private String fromDate;
    private String toDate;

    @Override
    public boolean isValid(Object localDate, ConstraintValidatorContext constraintValidatorContext) {

        try {
            LocalDate localFromDate = (LocalDate) getProperty(localDate, this.fromDate);
            LocalDate localToDate = (LocalDate) getProperty(localDate, this.toDate);

            if (localFromDate.isAfter(localToDate)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    @Override
    public void initialize(DateMatch constraintAnnotation) {
        fromDate = constraintAnnotation.fromDate();
        toDate = constraintAnnotation.toDate();

    }
}
