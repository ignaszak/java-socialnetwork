package net.ignaszak.socialnetwork.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@DatabaseSetup("classpath:data/postRepositoryDataset.xml")
public class PostRepositoryTests extends RepositoryTestsInitializer {

    @Autowired
    private PostRepository postRepository;
    @MockBean
    private Pageable pageable;

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
        Post post = postRepository.findById(3);
        Assert.assertEquals("Post 3", post.getText());
    }
}
