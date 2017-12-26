package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Comment;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.dto.ErrorDTO;
import net.ignaszak.socialnetwork.exception.ResourceNotFoundException;
import net.ignaszak.socialnetwork.service.comment.CommentService;
import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("rest-api/posts/{id}/comments")
public class PostsCommentsRestController {

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable("id") Integer id, Pageable page) {
        Post post = postService.getPostById(id);
        if (post == null) return new ResponseEntity<>(ErrorDTO.notFound(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(
            commentService.getCommentsByPost(post, page),
            HttpStatus.OK
        );
    }

    @PutMapping
    public ResponseEntity<?> add(@Valid @RequestBody Comment comment, @PathVariable("id") Integer id) {
        Post post = postService.getPostById(id);
        if (post == null) return new ResponseEntity<>(ErrorDTO.notFound(), HttpStatus.NOT_FOUND);
        comment.setPost(post);
        comment.setAuthor(userService.getCurrentUser());
        commentService.save(comment);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}
