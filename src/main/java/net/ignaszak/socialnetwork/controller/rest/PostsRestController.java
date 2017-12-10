package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.AccessDeniedException;
import net.ignaszak.socialnetwork.exception.ResourceNotFoundException;
import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("rest-api/posts")
public class PostsRestController {

    private PostService postService;
    private UserService userService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public Post add(@Valid @RequestBody Post post) {
        User currentUser = userService.getCurrentUser();
        post.setAuthor(currentUser);
        postService.save(post);
        return post;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {
        User currentUser = userService.getCurrentUser();
        Post post = postService.getPostById(id);
        if (post == null) throw new ResourceNotFoundException();
        if (! post.isAuthor(currentUser)) throw new AccessDeniedException();
        postService.delete(post);
    }
}
