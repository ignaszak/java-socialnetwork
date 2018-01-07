package net.ignaszak.socialnetwork.service.mailer;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.model.mail.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class MailerServiceImpl implements MailerService {

    private EmailSender emailSender;
    private String emailFromAddress;

    @Autowired
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Value("${app.mail.from.address}")
    public void setEmailFromAddress(String emailFromAddress) {
        this.emailFromAddress = emailFromAddress;
    }

    @Override
    public void sendRemindPassword(User user, String newPassword) throws MessagingException {
        emailSender.send(
            user.getEmail(),
            emailFromAddress,
            "Remind password",
            "Your new password is: " + newPassword + "\nPleas sign in to your account and change it in your profile settings."
        );
    }

    @Override
    public void sendActivationLink(User user, String activationLink) throws MessagingException {
        emailSender.send(
            user.getEmail(),
            emailFromAddress,
            "User activation",
            "Your activation link: " + activationLink
        );
    }
}
