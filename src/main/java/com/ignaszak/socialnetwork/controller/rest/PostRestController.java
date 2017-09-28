package com.ignaszak.socialnetwork.controller.rest;

import com.ignaszak.socialnetwork.domain.Post;
import com.ignaszak.socialnetwork.domain.User;
import com.ignaszak.socialnetwork.service.post.PostService;
import com.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Integer> add(@RequestBody Post post) {
        User currentUser = userService.getCurrentUser();
        post.setUser(currentUser);
        postService.savePost(post);
        return new ResponseEntity<>(post.getId(), HttpStatus.OK);
    }
}
