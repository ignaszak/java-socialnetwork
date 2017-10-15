package net.ignaszak.socialnetwork.bootstrap;

import net.ignaszak.socialnetwork.domain.Comment;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.Relation;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.service.comment.CommentService;
import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.relation.RelationService;
import net.ignaszak.socialnetwork.service.user.UserService;
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
    private RelationService relationService;

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

    @Autowired
    public void setRelationService(RelationService relationService) {
        this.relationService = relationService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)  {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin123");
        user.setEmail("admin@test.com");
        user.setRole("ROLE_ADMIN");
        user.setCaption("Some caption");
        user.setEnabled(true);
        userService.save(user);

        User user2 = new User();
        user2.setUsername("test");
        user2.setPassword("test123");
        user2.setEmail("test@test.com");
        user2.setRole("ROLE_ADMIN");
        user2.setCaption("Some caption");
        user2.setEnabled(true);
        userService.save(user2);

        try {
            relationService.accept(new Relation(user, user2));
        } catch (Exception e) {}

        Post post = new Post();
        post.setUser(user);
        post.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus bibendum quam a ligula suscipit, nec iaculis lacus cursus. Mauris sed turpis diam. Sed elit eros, aliquam id imperdiet fringilla, ornare eu est.");
        postService.save(post);

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Lorem ipsum dolor sit amet.");
        commentService.save(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Consectetur adipiscing elit.");
        commentService.save(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Vivamus bibendum quam a ligula suscipit.");
        commentService.save(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Nec iaculis lacus cursus.");
        commentService.save(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Mauris sed turpis diam.");
        commentService.save(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Sed elit eros.");
        commentService.save(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Sed elit eros.");
        commentService.save(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Sed elit eros.");
        commentService.save(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Sed elit eros.");
        commentService.save(comment);

        comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText("Sed elit eros.");
        commentService.save(comment);
    }
}
