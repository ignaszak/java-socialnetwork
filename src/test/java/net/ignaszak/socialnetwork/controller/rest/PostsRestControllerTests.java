package net.ignaszak.socialnetwork.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.EmptyPostException;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PostsRestController.class, secure = false)
public class PostsRestControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    PostService postService;
    @MockBean
    UserService userService;
    @MockBean
    Post post;
    @MockBean
    User user;

    @Test
    public void shouldAddPostIfRequiredDataAreSet() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Post post = new Post(new User(), new User(), "test post");
        mockMvc
            .perform(
                put("/rest-api/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(post))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.text").value("test post"))
            .andExpect(jsonPath("$.receiver").isNotEmpty());
        Mockito.verify(userService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(postService, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldNotAddPostIfDataAreMissing() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Post post = new Post(new User(), new User(), "");
        Mockito.when(postService.save(Mockito.any())).thenThrow(new EmptyPostException());
        mockMvc
            .perform(
                put("/rest-api/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(post))
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Post could not be empty"));
    }

    @Test
    public void shouldDeletePostIfExistsAndUserIsAuthor() throws Exception {
        Mockito.when(postService.getPostById(1)).thenReturn(post);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(post.isAuthor(user)).thenReturn(true);
        mockMvc
            .perform(delete("/rest-api/posts/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
        Mockito.verify(postService, Mockito.times(1)).delete(post);
    }

    @Test
    public void shouldNotDeletePostIfUserIsNotPostAuthor() throws Exception {
        Mockito.when(postService.getPostById(1)).thenReturn(post);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(post.isAuthor(user)).thenReturn(false);
        mockMvc
            .perform(delete("/rest-api/posts/{id}", 1))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Access denied"));
        Mockito.verify(postService, Mockito.never()).delete(post);
    }

    @Test
    public void shouldNotDeleteNotExistingPost() throws Exception {
        Mockito.when(postService.getPostById(1)).thenThrow(new NotFoundException());
        mockMvc
            .perform(delete("/rest-api/posts/{id}", 1))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Not found"));
        Mockito.verify(postService, Mockito.never()).delete(post);
    }
}
