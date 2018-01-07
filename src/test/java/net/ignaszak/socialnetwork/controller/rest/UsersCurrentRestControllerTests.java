package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.service.mailer.MailerService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UsersCurrentRestController.class, secure = false)
public class UsersCurrentRestControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    MailerService mailerService;
    private User user;

    @Before
    public void setUp() {
        user = new User("test", "test@ignaszak.net", "test1234");
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
    }

    @Test
    public void shouldReturnCurrentUser() throws Exception {
        mockMvc.perform(get("/rest-api/users/current"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("test"))
            .andExpect(jsonPath("$.email").value("test@ignaszak.net"))
            .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    public void shouldUpdateUserCaption() throws Exception {
        mockMvc
            .perform(
                post("/rest-api/users/current")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"caption\":\"some user caption\", \"email\":\"test@ignaszak.net\"}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.caption").value("some user caption"))
            .andExpect(jsonPath("$.email").value("test@ignaszak.net"));
        Mockito.verify(userService, Mockito.times(1)).save(user);
    }

    @Test
    public void shouldUpdateUserCaptionAndSetEmailAsUnverified() throws Exception {
        mockMvc
            .perform(
                post("/rest-api/users/current")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"caption\":\"some user caption\", \"email\":\"changedEmail@ignaszak.net\"}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.caption").value("some user caption"))
            .andExpect(jsonPath("$.email").value("test@ignaszak.net"));
        Mockito.verify(userService, Mockito.times(1)).save(user);
        Mockito.verify(mailerService, Mockito.times(1))
            .sendActivationLink(Mockito.any(), Mockito.anyString());
    }
}
