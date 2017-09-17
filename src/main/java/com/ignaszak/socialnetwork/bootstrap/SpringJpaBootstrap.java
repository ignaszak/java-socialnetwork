package com.ignaszak.socialnetwork.bootstrap;

import com.ignaszak.socialnetwork.domain.Comment;
import com.ignaszak.socialnetwork.domain.Post;
import com.ignaszak.socialnetwork.domain.User;
import com.ignaszak.socialnetwork.service.comment.CommentService;
import com.ignaszak.socialnetwork.service.post.PostService;
import com.ignaszak.socialnetwork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by tomek on 09.07.17.
 */
@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private PostService postService;
    private CommentService commentService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
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
        userService.saveUser(user);

        Post post = new Post();
        post.setUser(user);
        post.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum quam a ligula suscipit, nec iaculis lacus cursus. Mauris sed turpis diam. Sed elit eros, aliquam id imperdiet fringilla, ornare eu est.");
        postService.savePost(post);

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Lorem ipsum dolor sit amet.");
        commentService.saveComment(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Consectetur adipiscing elit.");
        commentService.saveComment(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Vivamus bibendum quam a ligula suscipit.");
        commentService.saveComment(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Nec iaculis lacus cursus.");
        commentService.saveComment(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Mauris sed turpis diam.");
        commentService.saveComment(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Sed elit eros.");
        commentService.saveComment(comment);
    }
}
