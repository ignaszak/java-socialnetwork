package net.ignaszak.socialnetwork.model.mail;

import javax.mail.MessagingException;

public interface EmailSender {
    void send(String to, String from, String subject, String message) throws MessagingException;
}
