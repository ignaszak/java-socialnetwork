package net.ignaszak.socialnetwork.controller;

import net.ignaszak.socialnetwork.service.media.MediaService;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MediaController.class, secure = false)
public class MediaControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MediaService mediaService;

    @Test
    public void shouldDisplayFileByFilename() throws Exception {
        String filename = "filename.jpg";
        TemporaryFolder tf = new TemporaryFolder();
        tf.create();
        File file = tf.newFile(filename);
        Resource resource = new UrlResource(file.toURI());
        given(mediaService.getOneResourceByFilename(filename)).willReturn(resource);
        mockMvc.perform(get("/public/medias/{filename}", file.getName()))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnNotFoundIfFileDoesNotExists() throws Exception {
        mockMvc.perform(get("/public/medias/{filename}", "notExistingFile.jpg"))
                .andExpect(status().isNotFound());
    }
}
