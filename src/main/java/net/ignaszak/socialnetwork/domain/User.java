package net.ignaszak.socialnetwork.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

/**
 * Created by tomek on 05.04.17.
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "new_email")
    private String newEmail;

    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "caption")
    private String caption;

    @Column(name = "profile")
    private String profile;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "status")
    private String status;

    @Column(name = "enabled")
    private boolean enabled;


    public User() {
        enabled = false;
    }

    public Integer getId() {
        return id;
    }

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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        enabled = true;
    }

    public boolean isEqualsTo(User user) {
        return this.username.equals(user.username);
    }

    public void activate() {
        activationCode = null;
        status = null;
        enabled = true;
    }

    public void newEmailAsMainEmail() {
        if (newEmail != null && ! newEmail.isEmpty()) {
            email = newEmail;
            newEmail = null;
        }
    }

    public void changeEmail(String newEmail, String activationCode) {
        this.newEmail = newEmail;
        this.activationCode = activationCode;
        status = "email_activation";
    }
}
