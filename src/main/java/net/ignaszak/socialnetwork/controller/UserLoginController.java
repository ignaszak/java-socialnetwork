package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.type.UserLoginType;
import net.ignaszak.socialnetwork.service.user.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class UserLoginController {

    private SecurityService securityService;

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public String login(Model model) {
        model.addAttribute("login", new UserLoginType());
        User user = securityService.findLoggedInUser();
        return user != null ? "redirect:/" : "login";
    }
}
