package net.ignaszak.socialnetwork.service.user;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.form.UserRegistrationForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.jws.soap.SOAPBinding;

/**
 * Created by tomek on 05.04.17.
 */
public interface UserService {
    Page<User> getAll(Pageable page);
    User getUserById(Integer id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User getUserFromUserRegistrationForm(UserRegistrationForm userRegistrationForm);
    User getCurrentUser();
    User getUserByActivationCode(String code);
    User getUserByEmailOrNewEmail(String email);
    User add(User user);
    User save(User user);
    void delete(User user);
    Page<User> getFriendsByUser(User user, Pageable page);
    Page<User> getInvitationsByUser(User user, Pageable page);
}
