package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.dto.ErrorDTO;
import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user == null) return new ResponseEntity<>(ErrorDTO.notFound(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/findByUsername", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByUsername(@Param("username") String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) return new ResponseEntity<>(ErrorDTO.notFound(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/findByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByEmailOrNewEmail(@Param("email") String email) {
        User user = userService.getUserByEmailOrNewEmail(email);
        if (user == null) return new ResponseEntity<>(ErrorDTO.notFound(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserPosts(@PathVariable Integer id, Pageable page) {
        User user = userService.getUserById(id);
        if (user == null) return new ResponseEntity<>(ErrorDTO.notFound(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(postService.getByUser(user, page), HttpStatus.OK);
    }
}
