package net.ignaszak.socialnetwork.service;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.repository.UserRepository;
import net.ignaszak.socialnetwork.service.user.UserService;
import net.ignaszak.socialnetwork.service.user.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class UserServiceTests {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setup() {
        User user = new User();
        user.setEmail("test@ignaszak.net");
        List<User> list = new ArrayList<>();
        list.add(user);

        Mockito.when(userRepository.findCurrentUser()).thenReturn(list);
    }

    @Test
    public void testGetCurrentUser() {
        User user = userService.getCurrentUser();
        Assert.assertEquals("test@ignaszak.net", user.getEmail());
    }
}
