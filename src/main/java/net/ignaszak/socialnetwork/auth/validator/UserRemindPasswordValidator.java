package net.ignaszak.socialnetwork.auth.validator;

import net.ignaszak.socialnetwork.service.user.UserService;
import net.ignaszak.socialnetwork.type.UserRemindPasswordType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserRemindPasswordValidator implements Validator {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserRemindPasswordType.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRemindPasswordType type = (UserRemindPasswordType) target;
        if (userService.getUserByEmail(type.getEmail()) == null) {
            errors.rejectValue("email", "Invalid.email","Email does not exists.");
        }
    }
}
