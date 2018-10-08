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

