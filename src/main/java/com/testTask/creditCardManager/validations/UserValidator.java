package com.testTask.creditCardManager.validations;

import com.testTask.creditCardManager.models.User;
import com.testTask.creditCardManager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by giulio.farrugia.
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Username cannot be empty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Password cannot be empty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmedPassword", "Password Confirmation cannot be empty!");

        if(user.getUsername().length() < 3 || user.getUsername().length() > 10){
            errors.rejectValue("username", "Length must be between 3 and 10");
        }

        if(user.getPassword().length() < 3 || user.getPassword().length() > 20){
            errors.rejectValue("password", "Length must be between 3 and 10");
        }

        // if password and confirmed password do not match
        if(!user.getPassword().equals(user.getConfirmedPassword())){
            errors.rejectValue("confirmedPassword", "Passwords do not match!");
        }

        // if another user by the same name already exists.
        if(userService.findByUsername(user.getUsername()) != null){
            errors.rejectValue("username","Username already exists!");
        }
    }
}
