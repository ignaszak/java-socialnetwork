package net.ignaszak.socialnetwork.type;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserRemindPasswordType {

    @Email
    @NotBlank(message = "Invalid email!")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
