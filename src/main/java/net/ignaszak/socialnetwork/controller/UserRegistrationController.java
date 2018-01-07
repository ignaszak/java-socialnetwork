package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.auth.validator.UserRegistrationTypeValidator;
import net.ignaszak.socialnetwork.service.mailer.MailerService;
import net.ignaszak.socialnetwork.type.UserRegistrationType;
import net.ignaszak.socialnetwork.service.user.SecurityService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private UserService userService;
    private UserRegistrationTypeValidator userRegistrationTypeValidator;
    private SecurityService securityService;
    private MailerService mailerService;

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserRegistrationTypeValidator(UserRegistrationTypeValidator userRegistrationTypeValidator) {
        this.userRegistrationTypeValidator = userRegistrationTypeValidator;
    }

    @Autowired
    public void setMailerService(MailerService mailerService) {
        this.mailerService = mailerService;
    }

    @InitBinder("registration")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userRegistrationTypeValidator);
    }

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("registration", new UserRegistrationType());
        User user = securityService.findLoggedInUser();
        return user != null ? "redirect:/" : "registration";
    }

    @PostMapping
    public String registration(
            @Valid @ModelAttribute("registration") UserRegistrationType userRegistrationForm,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        net.ignaszak.socialnetwork.domain.User user = userService.getUserFromUserRegistrationForm(userRegistrationForm);
        String code = UUID.randomUUID().toString();
        try {
            String activationLink = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + "/user/email-activation?code=" + code;
            mailerService.sendActivationLink(user, activationLink);
            user.setActivationCode(code);
        } catch (MessagingException e) {
            user.enable();
        }
        userService.add(user);

        return "redirect:/login?activation";
    }
}
