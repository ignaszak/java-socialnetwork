package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UsersRestController.class, secure = false)
@EnableSpringDataWebSupport
public class UsersRestControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    PostService postService;
    @MockBean
    UserService userService;
    @MockBean
    Pageable pageable;
    @MockBean
    User user;

    @Test
    public void shouldReturnUserById() throws Exception {
        User user = new User("test", "test@ignaszak.net", "test1234");
        Mockito.when(userService.getUserById(1)).thenReturn(user);
        mockMvc
            .perform(get("/rest-api/users/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("test"))
            .andExpect(jsonPath("$.email").value("test@ignaszak.net"));
    }

    @Test
    public void shouldReturnNotFoundIfCantFindUserById() throws Exception {
        Mockito.when(userService.getUserById(1)).thenThrow(new NotFoundException());
        mockMvc
            .perform(get("/rest-api/users/{id}", 1))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    public void shouldReturnUserByUsername() throws Exception {
        User user = new User("test", "test@ignaszak.net", "test1234");
        Mockito.when(userService.getUserByUsername("test")).thenReturn(user);
        mockMvc
            .perform(get("/rest-api/users/findByUsername").param("username", "test"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("test"))
            .andExpect(jsonPath("$.email").value("test@ignaszak.net"));
    }

    @Test
    public void shouldReturnNotFoundIfCantFindUserByUsername() throws Exception {
        Mockito.when(userService.getUserByUsername("test")).thenThrow(new NotFoundException());
        mockMvc
            .perform(get("/rest-api/users/findByUsername").param("username", "test"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    public void shouldReturnUserByEmail() throws Exception {
        User user = new User("test", "test@ignaszak.net", "test1234");
        Mockito.when(userService.getUserByEmailOrNewEmail("test@ignaszak.net")).thenReturn(user);
        mockMvc
            .perform(get("/rest-api/users/findByEmail").param("email", "test@ignaszak.net"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("test"))
            .andExpect(jsonPath("$.email").value("test@ignaszak.net"));
    }

    @Test
    public void shouldReturnNotFoundIfCantFindUserByEmail() throws Exception {
        Mockito.when(userService.getUserByEmailOrNewEmail("test@ignaszak.net")).thenThrow(new NotFoundException());
        mockMvc
            .perform(get("/rest-api/users/findByEmail").param("email", "test@ignaszak.net"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    public void shouldReturnNotFoundWhileAccessingPostFromNorExistingUser() throws Exception {
        Mockito.when(userService.getUserById(1)).thenThrow(new NotFoundException());
        mockMvc
            .perform(get("/rest-api/users/{id}/posts", 1))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    public void shouldReturnUserPosts() throws Exception {
        Mockito.when(userService.getUserById(1)).thenReturn(user);
        mockMvc
            .perform(get("/rest-api/users/{id}/posts", 1))
            .andExpect(status().isOk());
    }
}
