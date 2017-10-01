package net.ignaszak.socialnetwork.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by tomek on 10.04.17.
 */
public class UserLoginForm {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, max = 15)
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
