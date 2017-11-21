package net.ignaszak.socialnetwork.type;

import org.hibernate.validator.constraints.Email;

public class UserRemindPasswordType {

    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
