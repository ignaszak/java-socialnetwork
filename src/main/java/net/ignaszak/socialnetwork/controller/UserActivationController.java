package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserActivationController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/email-activation")
    public ModelAndView emailActivation(@RequestParam String code) {
        try {
            User user = userService.getUserByActivationCode(code);
            user.newEmailAsMainEmail();
            user.activate();
            userService.save(user);
            return new ModelAndView("activation_code", "success", true);
        } catch (NotFoundException e) {
            return new ModelAndView("activation_code", "success", false);
        }

    }
}
