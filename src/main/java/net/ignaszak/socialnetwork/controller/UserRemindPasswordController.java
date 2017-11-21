package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.auth.validator.UserRemindPasswordValidator;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.type.UserRemindPasswordType;
import net.ignaszak.socialnetwork.model.mail.EmailSender;
import net.ignaszak.socialnetwork.model.password.PasswordGenerator;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private EmailSender emailSender;
    private PasswordGenerator passwordGenerator;
    private UserRemindPasswordValidator userRemindPasswordValidator;
    private String emailFromAddress;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    @Autowired
    public void setUserRegistrationTypeValidator(UserRemindPasswordValidator userRemindPasswordValidator) {
        this.userRemindPasswordValidator = userRemindPasswordValidator;
    }

    @Value("${app.mail.from.address}")
    public void setEmailFromAddress(String emailFromAddress) {
        this.emailFromAddress = emailFromAddress;
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
        User user = userService.getUserByEmail(remindForm.getEmail());
        if (bindingResult.hasErrors() || user == null) {
            model.addAttribute("isEmailInvalid", true);
            return "remind";
        }
        String newPassword = passwordGenerator.generate();
        user.setPassword(newPassword);
        emailSender.send(
                user.getEmail(),
                emailFromAddress,
                "Remind password",
                "" +
                "Your new password is: " + newPassword +
                "\nPleas sign in to your account and change it in your profile settings."
        );
        userService.save(user);

        return "redirect:/login?remind";
    }
}
