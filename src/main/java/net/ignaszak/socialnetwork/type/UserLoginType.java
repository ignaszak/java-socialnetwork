package net.ignaszak.socialnetwork.type;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class UserLoginType {

    @NotBlank(message = "Invalid username")
    private String username;

    @Size(min = 8, max = 15, message = "Password must by 8-15 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
