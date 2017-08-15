package com.ignaszak.socialnetwork.auth.validator;

import com.ignaszak.socialnetwork.form.UserRegistrationForm;
import com.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by tomek on 09.04.17.
 */
@Component
public class UserRegistrationFormValidator implements Validator {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> c) {
        return c.equals(UserRegistrationForm.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserRegistrationForm form = (UserRegistrationForm) object;
        validateEmail(form, errors);
        validateUsername(form, errors);
        validatePasswords(form, errors);
    }

    private void validateEmail(UserRegistrationForm form, Errors errors) {
        if (userService.getUserByEmail(form.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.registration.email","User with this email already exists.");
        }
    }

    private void validateUsername(UserRegistrationForm form, Errors errors) {
        if (userService.getUserByUsername(form.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.registration.username", "User with this username already exists.");
        }
    }

    private void validatePasswords(UserRegistrationForm form, Errors errors) {
        if (! form.getPassword().equals(form.getPasswordRepeat())) {
            errors.rejectValue("password", "Diff.registration.password","Passwords do not match.");
        }
    }
}
