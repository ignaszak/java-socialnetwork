package net.ignaszak.socialnetwork.model.password;

import com.jcabi.matchers.RegexMatchers;
import org.junit.Assert;
import org.junit.Test;

public class PasswordGeneratorTests {

    @Test
    public void shouldReturnRandomPasswordWithMinimumOneDigit() {
        PasswordGenerator pg = new PasswordGenerator();
        String password = pg.generate();
        Assert.assertThat(password, RegexMatchers.matchesPattern(".*\\d+.*"));
        Assert.assertTrue(password.length() == 8);
    }
}
