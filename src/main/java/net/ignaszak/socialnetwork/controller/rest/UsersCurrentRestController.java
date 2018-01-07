package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.service.mailer.MailerService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("rest-api/users/current")
public class UsersCurrentRestController {

    private UserService userService;
    private MailerService mailerService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMailerService(MailerService mailerService) {
        this.mailerService = mailerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return userService.getCurrentUser();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User update(@RequestBody User user, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        currentUser.setCaption(user.getCaption());
        if (! currentUser.getEmail().equals(user.getEmail())) {
            String code = UUID.randomUUID().toString();
            currentUser.changeEmail(user.getEmail(), code);
            try {
                String activationLink = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + "/user/email-activation?code=" + code;
                mailerService.sendActivationLink(user, activationLink);
            } catch (MessagingException e) {
                user.activate();
            }
        }
        userService.save(currentUser);
        return currentUser;
    }
}
