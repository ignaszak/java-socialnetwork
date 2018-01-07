package net.ignaszak.socialnetwork.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@DatabaseSetup("classpath:data/postRepositoryDataset.xml")
public class PostRepositoryTests extends RepositoryTestsInitializer {

    @Autowired
    private PostRepository postRepository;
    @MockBean
    private Pageable pageable;
    @MockBean
    private Post emptyPost;

    @Test
    public void findPostsByUser() {
        User user = new User();
        user.setId(1);
        Page<Post> feeds = postRepository.findPostsByUser(user, pageable);
        Assert.assertEquals(4, feeds.getTotalElements());
    }

    @Test
    public void findFeedByUser() {
        User user = new User();
        user.setId(1);
        Page<Post> feeds = postRepository.findFeedByUser(user, pageable);
        Assert.assertEquals(5, feeds.getTotalElements());
    }

    @Test
    public void findById() {
        Optional<Post> post = postRepository.findById(3);
        Assert.assertTrue(post.isPresent());
        Assert.assertEquals("Post 3", post.orElse(emptyPost).getText());
    }
}
