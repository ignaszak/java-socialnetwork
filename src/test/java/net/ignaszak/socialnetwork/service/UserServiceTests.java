package net.ignaszak.socialnetwork.service;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.service.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@DataJpaTest
public class UserServiceTests {

    @Autowired
    private TestEntityManager entityManager;

    @MockBean
    private UserService userRepository;

    @Before
    public void setup() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin123");
        user.setEmail("admin@test.com");
        user.setRole("ROLE_ADMIN");
        user.setCaption("Some caption");
        user.setEnabled(true);
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    @WithMockUser(username = "admin")
    public void testTest() {
        User currentUser = userRepository.getCurrentUser();
        Assert.assertSame("admin", currentUser.getUsername());
    }
}
