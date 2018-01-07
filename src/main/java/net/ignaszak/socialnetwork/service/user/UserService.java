package net.ignaszak.socialnetwork.service.user;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.type.UserRegistrationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by tomek on 05.04.17.
 */
public interface UserService {
    Page<User> getAll(Pageable page);
    User getUserById(Integer id) throws NotFoundException;
    User getUserByUsername(String username) throws NotFoundException;
    User getUserByEmail(String email) throws NotFoundException;
    User getUserFromUserRegistrationForm(UserRegistrationType userRegistrationForm);
    User getCurrentUser();
    User getUserByActivationCode(String code) throws NotFoundException;
    User getUserByEmailOrNewEmail(String email) throws NotFoundException;
    User add(User user);
    User save(User user);
    void delete(User user);
    Page<User> getFriendsByUser(User user, Pageable page);
    Page<User> getInvitationsByCurrentUser(Pageable page);
}
