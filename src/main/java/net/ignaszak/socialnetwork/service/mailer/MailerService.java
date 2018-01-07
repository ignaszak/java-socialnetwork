package net.ignaszak.socialnetwork.service.mailer;

import net.ignaszak.socialnetwork.domain.User;

import javax.mail.MessagingException;

public interface MailerService {

    void sendRemindPassword(User user, String newPassword) throws MessagingException;
    void sendActivationLink(User user, String activationLink) throws MessagingException;
}
