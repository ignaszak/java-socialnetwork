package com.ignaszak.socialnetwork.controller.rest;

import com.ignaszak.socialnetwork.domain.User;
import com.ignaszak.socialnetwork.model.mail.EmailSender;
import com.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("rest-api/users/current")
public class CurrentUserRestController {

    private UserService userService;
    private EmailSender emailSender;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return userService.getCurrentUser();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User update(@RequestBody User user, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        currentUser.setCaption(user.getCaption());
        if (! currentUser.getEmail().equals(user.getEmail())) {
            currentUser.setNewEmail(user.getEmail());
            String code = UUID.randomUUID().toString();
            currentUser.setActivationCode(code);
            currentUser.setStatus("email_activation");
            emailSender.send(
                    user.getEmail(),
                    "Activation code",
                    "Your activation link: "
                            + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                            + "/user/email-activation?code=" + code
            );
        }
        userService.save(currentUser);
        currentUser.setActivationCode(null);
        return currentUser;
    }
}
