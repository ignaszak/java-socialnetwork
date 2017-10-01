package com.ignaszak.socialnetwork.controller;

import com.ignaszak.socialnetwork.domain.User;
import com.ignaszak.socialnetwork.service.user.UserService;
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
            user.setActivationCode(null);
            user.setStatus(null);
            if (user.getNewEmail() != null && ! user.getNewEmail().isEmpty()) {
                user.setEmail(user.getNewEmail());
            }
            user.setNewEmail(null);
            user.setEnabled(true);
            userService.save(user);
            return new ModelAndView("activation_code", "success", true);
        } catch (NullPointerException e) {
            return new ModelAndView("activation_code", "success", false);
        }

    }
}
