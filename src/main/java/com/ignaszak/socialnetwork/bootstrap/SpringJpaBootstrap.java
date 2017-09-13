package com.ignaszak.socialnetwork.bootstrap;

import com.ignaszak.socialnetwork.domain.Post;
import com.ignaszak.socialnetwork.domain.User;
import com.ignaszak.socialnetwork.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by tomek on 09.07.17.
 */
@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin123");
        user.setEmail("admin@test.com");
        user.setRole("ROLE_ADMIN");
        user.setCaption("Some caption");
        user.setEnabled(true);

        Post post = new Post();
        post.setUser(user);
        post.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum quam a ligula suscipit, nec iaculis lacus cursus. Mauris sed turpis diam. Sed elit eros, aliquam id imperdiet fringilla, ornare eu est.");
        postService.savePost(post);
    }
}
