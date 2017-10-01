package com.ignaszak.socialnetwork.controller.rest;

import com.ignaszak.socialnetwork.domain.Post;
import com.ignaszak.socialnetwork.domain.User;
import com.ignaszak.socialnetwork.exception.ResourceNotFoundException;
import com.ignaszak.socialnetwork.service.post.PostService;
import com.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest-api/users")
public class UserRestController {

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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByIdOrUsername(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user == null) throw new ResourceNotFoundException();
        return user;
    }

    @GetMapping(value = "/findByUsername", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByUsername(@Param("username") String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) throw new ResourceNotFoundException();
        return user;
    }

    @GetMapping(value = "/findByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByEmailOrNewEmail(@Param("email") String email) {
        User user = userService.getUserByEmailOrNewEmail(email);
        if (user == null) throw new ResourceNotFoundException();
        return user;
    }

    @GetMapping(value = "/{id}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Post> getUserPosts(@PathVariable Integer id, Pageable page) {
        User user = userService.getUserById(id);
        if (user == null) throw new ResourceNotFoundException();
        return postService.getByUser(user, page);
    }
}
