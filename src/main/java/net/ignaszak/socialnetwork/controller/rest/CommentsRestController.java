package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Comment;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.ResourceNotFoundException;
import net.ignaszak.socialnetwork.service.comment.CommentService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest-api/comments")
public class CommentsRestController {

    private CommentService commentService;
    private UserService userService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {
        User currentUser = userService.getCurrentUser();
        Comment comment = commentService.getCommentById(id);
        if (comment == null) throw new ResourceNotFoundException();
        if (comment.isAuthor(currentUser) || comment.getPost().isAuthor(currentUser))
            commentService.delete(comment);
    }
}
