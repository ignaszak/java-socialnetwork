package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Comment;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.service.comment.CommentService;
import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

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
    User user;
    private Page page;

    @Before
    public void setUp() {
        Comment comment = new Comment();
        List<Comment> comments = Arrays.asList(comment, comment);
        page = new PageImpl<>(comments);
    }

    @Test
    public void shouldReturnCommentsByPostId() throws Exception {
        Mockito.when(postService.getPostById(1)).thenReturn(post);
        Mockito.when(commentService.getCommentsByPost(Mockito.any(), Mockito.any())).thenReturn(page);
        mockMvc
            .perform(get("/rest-api/posts/{id}/comments", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    public void shouldNotAddCommentIfPostDoesNotExist() throws Exception {
        Mockito.when(postService.getPostById(1)).thenThrow(new NotFoundException());
        mockMvc
            .perform(get("/rest-api/posts/{id}/comments", 1))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
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
                    .content("{\"text\":\"test comment\",\"author\":{},\"post\":{}}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.author").isNotEmpty())
            .andExpect(jsonPath("$.post").isNotEmpty())
            .andExpect(jsonPath("$.text").value("test comment"));
        Mockito.verify(commentService, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldNotAddEmptyCommentToPost() throws Exception {
        Mockito.when(postService.getPostById(1)).thenReturn(post);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        mockMvc
            .perform(
                put("/rest-api/posts/{id}/comments", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"text\":\"\",\"author\":{},\"post\":{}}")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Comment must be not empty"));
        Mockito.verify(commentService, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void shouldNotAddCommentWithoutAutor() throws Exception {
        Mockito.when(postService.getPostById(1)).thenReturn(post);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        mockMvc
            .perform(
                put("/rest-api/posts/{id}/comments", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"text\":\"test comment\",\"post\":{}}")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Add author"));
        Mockito.verify(commentService, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void shouldNotAddCommentWithoutPost() throws Exception {
        Mockito.when(postService.getPostById(1)).thenReturn(post);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        mockMvc
            .perform(
                put("/rest-api/posts/{id}/comments", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"text\":\"test comment\",\"author\":{}}")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Add post"));
        Mockito.verify(commentService, Mockito.never()).save(Mockito.any());
    }
}
