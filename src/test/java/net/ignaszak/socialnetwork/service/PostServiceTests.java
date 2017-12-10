package net.ignaszak.socialnetwork.service;

import net.ignaszak.socialnetwork.domain.Media;
import net.ignaszak.socialnetwork.domain.Post;
import net.ignaszak.socialnetwork.exception.EmptyPostException;
import net.ignaszak.socialnetwork.repository.PostRepository;
import net.ignaszak.socialnetwork.service.media.MediaService;
import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.post.PostServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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
    PostService postService;
    @MockBean
    PostRepository postRepository;
    @MockBean
    MediaService mediaService;
    @MockBean
    Post post;
    @MockBean
    Media media;
    Set<Media> medias;

    @Before
    public void setUp() {
        medias = new HashSet<>();
        medias.add(media);
        medias.add(media);
        medias.add(media);
    }

    @Test
    public void shouldSavePostContainingOnlyMedia() {
        Mockito.when(mediaService.movePostMediasFromTemp(post)).thenReturn(medias);
        postService.save(post);
        Mockito.verify(postRepository, Mockito.times(1)).save(post);
    }

    @Test
    public void shouldSavePostContainingOnlyText() {
        Mockito.when(mediaService.movePostMediasFromTemp(post)).thenReturn(new HashSet<>());
        Mockito.when(post.getText()).thenReturn("Some text");
        postService.save(post);
        Mockito.verify(postRepository, Mockito.times(1)).save(post);
    }

    @Test
    public void shouldSavePostWithTextAndMedias() {
        Mockito.when(mediaService.movePostMediasFromTemp(post)).thenReturn(medias);
        Mockito.when(post.getText()).thenReturn("Some text");
        postService.save(post);
        Mockito.verify(postRepository, Mockito.times(1)).save(post);
    }

    @Test(expected = EmptyPostException.class)
    public void shouldNotSavePostWithoutBothTextAndMedias() {
        Mockito.when(mediaService.movePostMediasFromTemp(post)).thenReturn(new HashSet<>());
        Mockito.when(post.getText()).thenReturn("");
        postService.save(post);
        Mockito.verify(postRepository, Mockito.never()).save(post);
    }
}
