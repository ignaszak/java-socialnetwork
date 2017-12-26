package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.service.user.UserService;
import net.ignaszak.socialnetwork.type.UserChangePasswordType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("rest-api/users/current/password")
public class UsersCurrentPasswordRestController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity change(@Valid @RequestBody UserChangePasswordType change, Errors errors) {
        User current = userService.getCurrentUser();
        if (! change.isEqualsWithOldPassword(current.getPassword()))
            errors.reject("oldPassword", "oldPassword");
        if (! change.isNewPasswordConfirmed())
            errors.reject("newPasswordRepeat", "passwordRepeat");
        if (errors.hasErrors())
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        current.setPassword(change.getNewPassword());
        userService.save(current);
        return ResponseEntity.ok(current);
    }
}
