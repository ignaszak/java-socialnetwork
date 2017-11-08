package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.config.SecurityConfig;
import net.ignaszak.socialnetwork.service.user.SecurityService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = IndexController.class)
@Import(SecurityConfig.class)
public class IndexControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SecurityService securityService;
    @MockBean
    private User user;
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser
    public void shouldDisplayIndexPageWhenUserIsLoggedIn() throws Exception {
        Mockito.when(securityService.findLoggedInUser()).thenReturn(user);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void shouldDisplayLoginPageWhenUserIsLoggedOut() throws Exception {
        Mockito.when(securityService.findLoggedInUser()).thenReturn(null);
        mockMvc.perform(get("/"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/login"));
    }
}
