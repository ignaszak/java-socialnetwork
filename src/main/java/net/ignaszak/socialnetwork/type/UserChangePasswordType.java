package net.ignaszak.socialnetwork.type;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Size;

public class UserChangePasswordType {

    @Size(min = 8, max = 15, message = "Password must by 8-15 characters!")
    private String oldPassword;

    @Size(min = 8, max = 15, message = "Password must by 8-15 characters!")
    private String newPassword;

    @Size(min = 8, max = 15, message = "Password must by 8-15 characters!")
    private String newPasswordRepeat;

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPasswordRepeat(String newPasswordRepeat) {
        this.newPasswordRepeat = newPasswordRepeat;
    }

    public String getNewPasswordRepeat() {
        return newPasswordRepeat;
    }

    public boolean isEqualsWithOldPassword(String oldPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(this.oldPassword, oldPassword);
    }

    public boolean isNewPasswordConfirmed() {
        return newPassword.equals(newPasswordRepeat);
    }
}
