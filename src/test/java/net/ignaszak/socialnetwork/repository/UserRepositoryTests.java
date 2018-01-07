package net.ignaszak.socialnetwork.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.ignaszak.socialnetwork.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@DatabaseSetup("classpath:data/userRepositoryDataset.xml")
public class UserRepositoryTests extends RepositoryTestsInitializer {

    @Autowired
    private UserRepository userRepository;
    @MockBean
    private Pageable pageable;
    @MockBean
    private User emptyUser;

    @Test
    public void findUserByEmailOrNewEmail() {
        String email = "test2@ignaszak.net";
        String newEmail = "new-test2@ignaszak.net";
        Assert.assertEquals(
            "test2",
            userRepository.findUserByEmailOrNewEmail(email, email).orElse(emptyUser).getUsername()
        );
        Assert.assertEquals(
            "test2",
            userRepository.findUserByEmailOrNewEmail(newEmail, newEmail).orElse(emptyUser).getUsername()
        );
    }

    @Test
    public void findUserByUsername() {
        Assert.assertEquals(
            "test1",
            userRepository.findUserByUsername("test1").orElse(emptyUser).getUsername()
        );
    }

    @Test
    public void findUserByEmail() {
        Assert.assertEquals(
            "test3@ignaszak.net",
            userRepository.findUserByEmail("test3@ignaszak.net").orElse(emptyUser).getEmail()
        );
    }

    @Test
    public void findUserByActivationCode() {
        Assert.assertEquals(
            "test3@ignaszak.net",
            userRepository.findUserByActivationCode("uniqueActivationCode").orElse(emptyUser).getEmail()
        );
    }

    @Test
    public void findFriendsByUser() {
        User user = new User();
        user.setId(1);
        Page<User> friends = userRepository.findFriendsByUser(user, pageable);
        Assert.assertEquals(2, friends.getTotalElements());
    }

    @Test
    public void findInvitationsByUser() {
        User user = new User();
        user.setId(1);
        Page<User> friends = userRepository.findInvitationsByUser(user, pageable);
        Assert.assertEquals(1, friends.getTotalElements());
    }
}
