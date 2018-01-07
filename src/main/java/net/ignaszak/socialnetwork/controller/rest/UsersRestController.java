package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest-api/users")
public class UsersRestController {

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
    public Page<User> getAll(Pageable page) {
        return userService.getAll(page);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable Integer id) throws NotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping(value = "/findByUsername", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByUsername(@Param("username") String username) throws NotFoundException {
        return userService.getUserByUsername(username);
    }

    @GetMapping(value = "/findByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByEmailOrNewEmail(@Param("email") String email) throws NotFoundException {
        return userService.getUserByEmailOrNewEmail(email);
    }

    @GetMapping(value = "/{id}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Post> getUserPosts(@PathVariable Integer id, Pageable page) throws NotFoundException {
        User user = userService.getUserById(id);
        return postService.getByUser(user, page);
    }
}
