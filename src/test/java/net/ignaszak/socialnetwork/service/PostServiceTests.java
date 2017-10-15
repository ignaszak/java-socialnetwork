package net.ignaszak.socialnetwork.service;

import net.ignaszak.socialnetwork.repository.PostRepository;
import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.post.PostServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PostServiceTests {

    @TestConfiguration
    static class PostServiceImplTestContextConfiguration {
        @Bean
        public PostService postService() {
            return new PostServiceImpl();
        }
    }

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;
}
