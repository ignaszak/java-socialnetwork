package net.ignaszak.socialnetwork.type;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by tomek on 05.04.17.
 */
public class UserRegistrationType {

    @NotBlank(message = "Invalid email")
    @Email
    private String email;

    @NotBlank(message = "Invalid username")
    private String username;

    @Size(min = 8, max = 15, message = "Password must by 8-15 characters")
    private String password;

    @Size(min = 8, max = 15, message = "Password must by 8-15 characters")
    private String passwordRepeat;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getRole() {
        return "USER";
    }
}
