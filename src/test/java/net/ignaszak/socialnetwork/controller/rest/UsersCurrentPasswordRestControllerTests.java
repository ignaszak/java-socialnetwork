package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UsersCurrentPasswordRestController.class, secure = false)
public class UsersCurrentPasswordRestControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    UserDetailsService userDetailsService;
    private User user;

    @Before
    public void setUp() {
        user = new User("test", "test@ignaszak.net", "test1234");
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
    }

    @Test
    public void shouldChangePasswordIfAllDataAreCorrect() throws Exception {
        mockMvc
            .perform(
                post("/rest-api/users/current/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"oldPassword\":\"test1234\",\"newPassword\":\"newPassword\",\"newPasswordRepeat\":\"newPassword\"}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("test"))
            .andExpect(jsonPath("$.email").value("test@ignaszak.net"))
            .andExpect(jsonPath("$.role").value("USER"));
        Mockito.verify(userService, Mockito.times(1)).save(user);
    }

    @Test
    public void shouldReturnErrorMessageIfOldPasswordIsIncorrect() throws Exception {
        mockMvc
            .perform(
                post("/rest-api/users/current/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"oldPassword\":\"incorrectPass\",\"newPassword\":\"newPassword\",\"newPasswordRepeat\":\"newPassword\"}")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].defaultMessage").value("oldPassword"));
        Mockito.verify(userService, Mockito.never()).save(user);
    }

    @Test
    public void shouldReturnErrorMessageIfOldAndNewPasswordsAreIncorrect() throws Exception {
        mockMvc
            .perform(
                post("/rest-api/users/current/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"oldPassword\":\"incorrectPass\",\"newPassword\":\"newPassword\",\"newPasswordRepeat\":\"newPassword1\"}")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].defaultMessage").value("oldPassword"))
            .andExpect(jsonPath("$.[1].defaultMessage").value("passwordRepeat"));
        Mockito.verify(userService, Mockito.never()).save(user);
    }
}
