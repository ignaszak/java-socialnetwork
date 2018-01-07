package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.auth.validator.UserRemindPasswordValidator;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.service.mailer.MailerService;
import net.ignaszak.socialnetwork.type.UserRemindPasswordType;
import net.ignaszak.socialnetwork.model.password.PasswordGenerator;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
@RequestMapping("/remind")
public class UserRemindPasswordController {

    private UserService userService;
    private MailerService mailerService;
    private PasswordGenerator passwordGenerator;
    private UserRemindPasswordValidator userRemindPasswordValidator;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMailerService(MailerService mailerService) {
        this.mailerService = mailerService;
    }

    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    @Autowired
    public void setUserRegistrationTypeValidator(UserRemindPasswordValidator userRemindPasswordValidator) {
        this.userRemindPasswordValidator = userRemindPasswordValidator;
    }

    @InitBinder("remind")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userRemindPasswordValidator);
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("remind", new UserRemindPasswordType());
        return "remind";
    }

    @PostMapping
    public String remind(
            @Valid @ModelAttribute("remind") UserRemindPasswordType remindForm,
            BindingResult bindingResult,
            Model model
    ) throws MessagingException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEmailInvalid", true);
            return "remind";
        }

        try {
            User user = userService.getUserByEmail(remindForm.getEmail());
            String newPassword = passwordGenerator.generate();
            user.setPassword(newPassword);
            userService.save(user);
            mailerService.sendRemindPassword(user, newPassword);
        } catch (NotFoundException e) {
            model.addAttribute("isEmailInvalid", true);
            return "remind";
        }

        return "redirect:/login?remind";
    }
}
