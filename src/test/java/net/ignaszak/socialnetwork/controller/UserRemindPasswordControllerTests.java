package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.auth.validator.UserRemindPasswordValidator;
import net.ignaszak.socialnetwork.config.SecurityConfig;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.model.mail.EmailSender;
import net.ignaszak.socialnetwork.model.password.PasswordGenerator;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserRemindPasswordController.class)
@Import(SecurityConfig.class)
public class UserRemindPasswordControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    EmailSender emailSender;
    @MockBean
    PasswordGenerator passwordGenerator;
    @MockBean
    UserDetailsService userDetailsService;
    @MockBean
    User user;
    @MockBean
    UserRemindPasswordValidator userRemindPasswordValidator;

    @Before
    public void before() {
        Mockito.when(userRemindPasswordValidator.supports(Mockito.any())).thenReturn(true);
    }

    @Test
    public void shouldDisplayRemindPasswordForm() throws Exception {
        mockMvc.perform(get("/remind"))
                .andExpect(status().isOk())
                .andExpect(view().name("remind"));
    }

    @Test
    public void shouldSendEmailIfFormIsCorrectlySend() throws Exception {
        String email = "test@ignaszak.net";
        Mockito.when(userService.getUserByEmail(email)).thenReturn(user);
        mockMvc.perform(post("/remind").with(csrf()).param("email", email))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/login?remind"));
        Mockito.verify(emailSender, Mockito.times(1))
                .send(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void shouldDisplayFormIfEmailIsIncorrect() throws Exception {
        String email = "incorrectEmail";
        mockMvc.perform(post("/remind").with(csrf()).param("email", email))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isEmailInvalid", true));
    }

    @Test
    public void shouldDisplayFormIfEmailIsNotFound() throws Exception {
        String email = "test@ignaszak.net";
        Mockito.when(userService.getUserByEmail(email)).thenReturn(null);
        mockMvc.perform(post("/remind").with(csrf()).param("email", email))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isEmailInvalid", true));
    }
}
