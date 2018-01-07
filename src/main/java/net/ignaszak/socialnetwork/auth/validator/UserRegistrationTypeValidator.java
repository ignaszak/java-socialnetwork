package net.ignaszak.socialnetwork.auth.validator;

import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.type.UserRegistrationType;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserRegistrationTypeValidator implements Validator {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> c) {
        return c.equals(UserRegistrationType.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserRegistrationType type = (UserRegistrationType) object;
        validateEmail(type, errors);
        validateUsername(type, errors);
        validatePasswords(type, errors);
    }

    private void validateEmail(UserRegistrationType type, Errors errors) {
        try {
            userService.getUserByEmail(type.getEmail());
        } catch (NotFoundException e) {
            errors.rejectValue(
                "email",
                "Duplicate.registration.email",
                "User with this email already exists!"
            );
        }
    }

    private void validateUsername(UserRegistrationType type, Errors errors) {
        try {
            userService.getUserByUsername(type.getUsername());
        } catch (NotFoundException e) {
            errors.rejectValue(
                "username",
                "Duplicate.registration.username",
                "User with this username already exists!"
            );
        }
    }

    private void validatePasswords(UserRegistrationType type, Errors errors) {
        if (! type.getPassword().equals(type.getPasswordRepeat())) {
            errors.rejectValue(
                "password",
                "Diff.registration.password",
                "Passwords don't match!"
            );
        }
    }
}
