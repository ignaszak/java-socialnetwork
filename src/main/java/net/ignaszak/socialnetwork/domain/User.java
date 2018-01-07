package net.ignaszak.socialnetwork.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank(message = "Add email")
    private String email;

    @Column(name = "new_email")
    @Email
    private String newEmail;

    @Column(name = "username", nullable = false, unique = true, updatable = false)
    @NotBlank(message = "Add username")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Add password")
    private String password;

    @Column(name = "role", nullable = false)
    @NotBlank(message = "Add role")
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

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        role = "USER";
        setPassword(password);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean equals(User user) {
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
