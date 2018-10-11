package com.arun.springbootvalidation.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collection;

@Component
public class CollectionValidator implements Validator {

    @Autowired
    private Validator collectionValidator;

    public CollectionValidator(LocalValidatorFactoryBean localValidatorFactoryBean) {
        this.collectionValidator = localValidatorFactoryBean;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Collection.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o instanceof Collection) {
            Collection collection = (Collection) o;
            collection.forEach(object -> {
                ValidationUtils.invokeValidator(collectionValidator, object, errors);
            });
        }
    }
}
