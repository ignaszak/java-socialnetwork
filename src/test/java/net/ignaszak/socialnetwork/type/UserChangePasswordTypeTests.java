package net.ignaszak.socialnetwork.type;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserChangePasswordTypeTests {

    UserChangePasswordType type = new UserChangePasswordType();

    @Test
    public void testCheckOldPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String oldPassword = encoder.encode("oldPassword");
        type.setOldPassword("oldPassword");
        Assert.assertTrue(type.isEqualsWithOldPassword(oldPassword));

        type.setOldPassword("oldPassword");
        Assert.assertFalse(type.isEqualsWithOldPassword("oldPassword"));
    }
}
