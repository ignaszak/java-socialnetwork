package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.service.post.PostService;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FeedRestController.class, secure = false)
@EnableSpringDataWebSupport
public class FeedRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    PostService postService;
    @MockBean
    Pageable pageable;

    @Test
    public void shouldReturnFeed() throws Exception {
        mockMvc.perform(get("/rest-api/feed")).andExpect(status().isOk());
    }
}
