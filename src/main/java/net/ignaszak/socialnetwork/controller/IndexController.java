package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.service.user.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by tomek on 16.04.17.
 */
@Controller
public class IndexController {

    private SecurityService securityService;

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/")
    public String home() {
        return redirectIfUserIsNotLoggedIn();
    }

    @GetMapping(value = {"/{u:[\\w]+}", "/{u:[\\w]+}/{a:[\\w]+}"})
    public String redirect() {
        return redirectIfUserIsNotLoggedIn();
    }

    private String redirectIfUserIsNotLoggedIn() {
        User user = securityService.findLoggedInUser();
        if (user != null) {
            return "index";
        }
        return "redirect:/login";

    }
}
