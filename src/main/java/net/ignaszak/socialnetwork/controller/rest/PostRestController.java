package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest-api/posts")
public class PostRestController {

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public Page<Post> findAll(Pageable page) {
        return postService.getAll(page);
    }

    @PutMapping
    public ResponseEntity<Integer> add(@RequestBody Post post) {
        User currentUser = userService.getCurrentUser();
        post.setUser(currentUser);
        postService.save(post);
        return new ResponseEntity<>(post.getId(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {
        User currentUser = userService.getCurrentUser();
        Post post = postService.getPostById(id);
        if (post.isAuthor(currentUser))
            postService.delete(post);
    }
}
