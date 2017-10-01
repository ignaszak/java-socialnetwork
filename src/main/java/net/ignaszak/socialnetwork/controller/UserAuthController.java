package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.auth.validator.UserRegistrationFormValidator;
import net.ignaszak.socialnetwork.form.UserLoginForm;
import net.ignaszak.socialnetwork.form.UserRegistrationForm;
import net.ignaszak.socialnetwork.model.mail.EmailSender;
import net.ignaszak.socialnetwork.service.user.SecurityService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Created by tomek on 10.04.17.
 */
@Controller
public class UserAuthController {

    private UserService userService;
    private UserRegistrationFormValidator userRegistrationFormValidator;
    private SecurityService securityService;
    private EmailSender emailSender;

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserRegistrationFormValidator(UserRegistrationFormValidator userRegistrationFormValidator) {
        this.userRegistrationFormValidator = userRegistrationFormValidator;
    }

    @Autowired
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @InitBinder("registration")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userRegistrationFormValidator);
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new UserLoginForm());
        return redirectToIndexIfUserIsLoggedIn("login");
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registration", new UserRegistrationForm());
        return redirectToIndexIfUserIsLoggedIn("registration");
    }

    @PostMapping("/registration")
    public String registration(
            @Valid @ModelAttribute("registration") UserRegistrationForm userRegistrationForm,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        net.ignaszak.socialnetwork.domain.User user = userService.getUserFromUserRegistrationForm(userRegistrationForm);
        String code = UUID.randomUUID().toString();
        try {
            userService.add(user);
            emailSender.send(
                    user.getEmail(),
                    "User activation",
                    "Your activation link: "
                            + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                            + "/user/email-activation?code=" + code
            );
            user.setEnabled(false);
            user.setActivationCode(code);
        } catch (Throwable e) {
            user.setEnabled(true);
        }
        return "redirect:/login?activation";
    }

    private String redirectToIndexIfUserIsLoggedIn(String view) {
        User user = securityService.findLoggedInUser();
        if (user != null) {
            return "redirect:/";
        }
        return view;
    }
}
