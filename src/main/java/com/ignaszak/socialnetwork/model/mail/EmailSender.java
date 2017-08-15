package com.ignaszak.socialnetwork.model.mail;

public interface EmailSender {
    void send(String to, String subject, String Content);
}
