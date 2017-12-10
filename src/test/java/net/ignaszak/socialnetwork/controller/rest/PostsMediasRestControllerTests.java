package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.service.media.MediaService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PostsMediasRestController.class, secure = false)
public class PostsMediasRestControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    MediaService mediaService;
    @MockBean
    UserService userService;

    @Test
    public void shouldUploadImage() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
            "image",
            "image.jpg",
            "image/jpeg",
            "fakeImageContent".getBytes()
        );
        String key = "uniqueKey";
        mockMvc.perform(
            fileUpload("/rest-api/posts/medias/{key}", key).file(image)
        ).andExpect(status().isOk());
        Mockito.verify(mediaService, Mockito.times(1))
            .saveTempImageWithUserAndKey(Matchers.eq(image), Mockito.any(), Matchers.eq(key));
    }

    @Test
    public void shouldDeleteImage() throws Exception {
        mockMvc.perform(delete("/rest-api/posts/medias/{id}", 1)).andExpect(status().isOk());
        Mockito.verify(mediaService, Mockito.times(1))
            .deleteByIdAndAuthor(Matchers.eq(1), Mockito.any());
    }
}
