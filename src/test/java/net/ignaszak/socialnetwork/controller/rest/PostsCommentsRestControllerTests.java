package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.service.comment.CommentService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PostsCommentsRestController.class, secure = false)
@EnableSpringDataWebSupport
public class PostsCommentsRestControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CommentService commentService;
    @MockBean
    UserService userService;
    @MockBean
    PostService postService;
    @MockBean
    Post post;
    @MockBean
    Pageable pageable;
    @MockBean
    User user;

    @Test
    public void shouldReturnCommentsByPostId() throws Exception {
        Mockito.when(postService.getPostById(1)).thenReturn(post);
        mockMvc
            .perform(get("/rest-api/posts/{id}/comments", 1))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundIfPostDoesNotExist() throws Exception {
        mockMvc
            .perform(get("/rest-api/posts/{id}/comments", 1))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    public void shouldAddCommentToPost() throws Exception {
        Mockito.when(postService.getPostById(1)).thenReturn(new Post());
        Mockito.when(userService.getCurrentUser()).thenReturn(new User());
        mockMvc
            .perform(
                put("/rest-api/posts/{id}/comments", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"text\":\"test comment\"}")
            )
            .andExpect(status().isOk());
        Mockito.verify(commentService, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldNotAddEmptyCommentToPost() throws Exception {
        Mockito.when(postService.getPostById(1)).thenReturn(post);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        mockMvc
            .perform(put("/rest-api/posts/{id}/comments", 1))
            .andExpect(status().isBadRequest());
        Mockito.verify(commentService, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void shouldNotAddCommentIfPostDoesNotExist() throws Exception {
        mockMvc
            .perform(
                put("/rest-api/posts/{id}/comments", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"text\":\"test comment\"}")
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Not found"));
    }
}
