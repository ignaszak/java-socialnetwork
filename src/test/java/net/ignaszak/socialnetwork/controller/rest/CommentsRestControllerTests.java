package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Comment;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.NotFoundException;
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

    @Test
    public void shouldDeleteCommentIfUserCanHandleComment() throws Exception {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(commentService.getCommentById(1)).thenReturn(comment);
        Mockito.when(comment.canHandle(user)).thenReturn(true);
        mockMvc
            .perform(delete("/rest-api/comments/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
        Mockito.verify(commentService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    public void shouldReturnAccessDeniedIfUserCanNotHandleComment() throws Exception {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(commentService.getCommentById(1)).thenReturn(comment);
        Mockito.when(comment.canHandle(user)).thenReturn(false);
        mockMvc
            .perform(delete("/rest-api/comments/{id}", 1))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Access denied"));
        Mockito.verify(commentService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    public void shouldReturnNotFoundIfCommentDoesNotExists() throws Exception {
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(commentService.getCommentById(Mockito.anyInt())).thenThrow(new NotFoundException());
        mockMvc
            .perform(delete("/rest-api/comments/{id}", 2))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Not found"));
        Mockito.verify(commentService, Mockito.never()).delete(Mockito.any());
    }
}
