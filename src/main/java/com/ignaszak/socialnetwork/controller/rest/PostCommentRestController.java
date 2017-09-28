package com.ignaszak.socialnetwork.controller.rest;

import com.ignaszak.socialnetwork.domain.Comment;
import com.ignaszak.socialnetwork.service.comment.CommentService;
import com.ignaszak.socialnetwork.service.post.PostService;
import com.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest-api/posts/{id}/comments")
public class PostCommentRestController {

    private CommentService commentService;

    private UserService userService;

    private PostService postService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Integer> add(@RequestBody Comment comment, @PathVariable("id") Integer id) {
        comment.setPost(postService.getById(id));
        comment.setUser(userService.getCurrentUser());
        commentService.saveComment(comment);
        return new ResponseEntity<>(comment.getId(), HttpStatus.OK);
    }
}
