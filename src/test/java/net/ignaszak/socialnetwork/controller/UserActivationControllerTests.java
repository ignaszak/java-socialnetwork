package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserActivationController.class, secure = false)
public class UserActivationControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @SpyBean
    User user;

    @Test
    public void shouldActivateUserWhenCodeIsCorrect() throws Exception {
        String activationCode = UUID.randomUUID().toString();
        Mockito.when(userService.getUserByActivationCode(activationCode)).thenReturn(user);
        mockMvc.perform(get("/user/email-activation").param("code", activationCode))
                .andExpect(status().isOk())
                .andExpect(model().attribute("success", true));
        Assert.assertTrue(user.isEnabled());
    }

    @Test
    public void shouldDisableUserWhenActivationCodeIsIncorrect() throws Exception {
        String activationCode = "invalidCode";
        Mockito.when(userService.getUserByActivationCode(activationCode)).thenReturn(null);
        mockMvc.perform(get("/user/email-activation").param("code", activationCode))
                .andExpect(status().isOk())
                .andExpect(model().attribute("success", false));
    }
}
