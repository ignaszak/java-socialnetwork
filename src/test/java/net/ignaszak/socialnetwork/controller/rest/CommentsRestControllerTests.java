package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Comment;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.service.comment.CommentService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CommentsRestController.class, secure = false)
public class CommentsRestControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CommentService commentService;
    @MockBean
    UserService userService;
    @MockBean
    User user;
    @MockBean
    Comment comment;
    @MockBean
    Post post;

    @Test
    public void shouldDeleteCommentIfUserIsAuthorOfPost() throws Exception {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(commentService.getCommentById(1)).thenReturn(comment);
        Mockito.when(comment.isAuthor(Mockito.any())).thenReturn(false);
        Mockito.when(comment.getPost()).thenReturn(post);
        Mockito.when(post.isAuthor(Mockito.any())).thenReturn(true);
        mockMvc.perform(delete("/rest-api/comments/{id}", 1)).andExpect(status().isOk());
        Mockito.verify(commentService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    public void shouldDeleteCommentIfUserIsAuthorOfComment() throws Exception {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(commentService.getCommentById(1)).thenReturn(comment);
        Mockito.when(comment.isAuthor(Mockito.any())).thenReturn(true);
        Mockito.when(comment.getPost()).thenReturn(post);
        Mockito.when(post.isAuthor(Mockito.any())).thenReturn(false);
        mockMvc.perform(delete("/rest-api/comments/{id}", 1)).andExpect(status().isOk());
        Mockito.verify(commentService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    public void shouldReturnNotFoundIfCommentDoesntExists() throws Exception {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(commentService.getCommentById(1)).thenReturn(comment);
        mockMvc.perform(delete("/rest-api/comments/{id}", 2)).andExpect(status().isNotFound());
        Mockito.verify(commentService, Mockito.never()).delete(Mockito.any());
    }
}
