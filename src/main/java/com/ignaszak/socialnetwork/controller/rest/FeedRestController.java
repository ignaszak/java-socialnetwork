package com.ignaszak.socialnetwork.controller.rest;

import com.ignaszak.socialnetwork.domain.Post;
import com.ignaszak.socialnetwork.domain.User;
import com.ignaszak.socialnetwork.service.post.PostService;
import com.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest-api/feed")
public class FeedRestController {

    private UserService userService;
    private PostService postService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Post> getFeed(Pageable page) {
        User currentUser = userService.getCurrentUser();
        return postService.getFeedByUser(currentUser, page);
    }
}
