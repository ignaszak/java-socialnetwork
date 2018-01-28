package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Comment;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.service.comment.CommentService;
import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
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
    public Page<Comment> get(@PathVariable("id") Integer id, Pageable page) throws NotFoundException {
        Post post = postService.getPostById(id);
        return commentService.getCommentsByPost(post, page);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment add(
        @Valid @RequestBody Comment comment,
        @PathVariable("id") Integer id
    ) throws NotFoundException {
        Post post = postService.getPostById(id);
        comment.setPost(post);
        comment.setAuthor(userService.getCurrentUser());
        return commentService.save(comment);
    }
}
