package net.ignaszak.socialnetwork.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CommentTests {

    private Comment comment;
    private User user;
    private User otherUser;
    private Post post;

    @Before
    public void setUp() {
        user = Mockito.spy(User.class);
        user.setUsername("test");
        post = Mockito.spy(Post.class);
        otherUser = Mockito.spy(User.class);
        otherUser.setUsername("OtherUser");
        comment = new Comment();
        comment.setPost(post);
    }

    @Test
    public void shouldHandleCommentWhenUserIsAuthorOfComment() {
        comment.setAuthor(user);
        Assert.assertTrue(comment.canHandle(user));
    }

    @Test
    public void shouldHandleCommentWhenUserIsCommentAndPostAuthor() {
        comment.setAuthor(user);
        post.setAuthor(user);
        Assert.assertTrue(comment.canHandle(user));
    }

    @Test
    public void shouldHandleCommentIfUserIsPostAuthorButNotComment() {
        post.setAuthor(user);
        comment.setAuthor(otherUser);
        Assert.assertTrue(comment.canHandle(user));
    }

    @Test
    public void shouldNotHandleCommentWhenUserIsNotCommentAndPostAuthor() {
        post.setAuthor(otherUser);
        comment.setAuthor(otherUser);
        Assert.assertFalse(comment.canHandle(user));
    }
}
