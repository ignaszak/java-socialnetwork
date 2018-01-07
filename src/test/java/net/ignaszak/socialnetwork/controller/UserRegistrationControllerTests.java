package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.auth.validator.UserRegistrationTypeValidator;
import net.ignaszak.socialnetwork.config.SecurityConfig;
import net.ignaszak.socialnetwork.model.mail.EmailSender;
import net.ignaszak.socialnetwork.service.mailer.MailerService;
import net.ignaszak.socialnetwork.service.user.SecurityService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.WebDataBinder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserRegistrationController.class)
@Import(SecurityConfig.class)
public class UserRegistrationControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    SecurityService securityService;
    @MockBean
    UserService userService;
    @MockBean
    UserRegistrationTypeValidator userRegistrationFormValidator;
    @MockBean
    MailerService mailerService;
    @MockBean
    UserDetailsService userDetailsService;
    @MockBean
    User user;
    @MockBean
    WebDataBinder webDataBinder;

    @Before
    public void before() {
        Mockito.when(userRegistrationFormValidator.supports(Mockito.any())).thenReturn(true);
    }

    @Test
    @WithMockUser
    public void shouldRedirectToIndexWhenUserIsLogged() throws Exception {
        Mockito.when(securityService.findLoggedInUser()).thenReturn(user);
        mockMvc.perform(get("/registration"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void shouldDisplayRegistrationFormWhenUserIsNotLogged() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }
}
