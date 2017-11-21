package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.config.SecurityConfig;
import net.ignaszak.socialnetwork.service.user.SecurityService;
import net.ignaszak.socialnetwork.service.user.UserService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserLoginController.class)
@Import(SecurityConfig.class)
public class UserLoginControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    SecurityService securityService;
    @MockBean
    UserService userService;
    @MockBean
    UserDetailsService userDetailsService;
    @MockBean
    User user;

    @Test
    @WithMockUser
    public void shouldRedirectToIndexWhenUserIsLogged() throws Exception {
        Mockito.when(securityService.findLoggedInUser()).thenReturn(user);
        mockMvc.perform(get("/login"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void shouldDisplayLoginFormWhenUserIsNotLogged() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
