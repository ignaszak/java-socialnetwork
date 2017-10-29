package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.model.mail.EmailSender;
import net.ignaszak.socialnetwork.service.relation.RelationService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("rest-api/users/current")
public class CurrentUserRestController {

    private UserService userService;
    private EmailSender emailSender;
    private RelationService relationService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Autowired
    public void setRelationService(RelationService relationService) {
        this.relationService = relationService;
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
            String code = UUID.randomUUID().toString();
            currentUser.changeEmail(user.getEmail(), code);
            emailSender.send(
                    user.getEmail(),
                    "Activation code",
                    "Your activation link: "
                            + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                            + "/user/email-activation?code=" + code
            );
        }
        userService.save(currentUser);
        //currentUser.setActivationCode(null);
        return currentUser;
    }

    @GetMapping(value = "/invitations", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<User> getInvitations(Pageable page) {
        return userService.getInvitationsByCurrentUser(page);
    }

    @GetMapping(value = "/invitations/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer countInvitations() {
        return relationService.countInvitationsByCurrentUser();
    }
}
