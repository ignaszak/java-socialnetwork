package net.ignaszak.socialnetwork.service.user;

import net.ignaszak.socialnetwork.domain.User;
import net.ignaszak.socialnetwork.exception.NotFoundException;
import net.ignaszak.socialnetwork.type.UserRegistrationType;
import net.ignaszak.socialnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by tomek on 05.04.17.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAll(Pageable page) {
        return userRepository.findAll(page);
    }

    @Override
    public User getUserById(Integer id) throws NotFoundException {
        return userRepository.findUserById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public User getUserByUsername(String username) throws NotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(NotFoundException::new);
    }

    @Override
    public User getUserByEmail(String email) throws NotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(NotFoundException::new);
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserFromUserRegistrationForm(UserRegistrationType userRegistrationForm) {
        User user = new User(
            userRegistrationForm.getUsername(),
            userRegistrationForm.getEmail(),
            userRegistrationForm.getPassword()
        );
        user.setRole(userRegistrationForm.getRole());
        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findCurrentUser().get(0);
    }

    @Override
    public User getUserByActivationCode(String code) throws NotFoundException {
        return userRepository.findUserByActivationCode(code).orElseThrow(NotFoundException::new);
    }

    @Override
    public User getUserByEmailOrNewEmail(String email) throws NotFoundException {
        return userRepository.findUserByEmailOrNewEmail(email, email).orElseThrow(NotFoundException::new);
    }

    @Override
    public Page<User> getFriendsByUser(User user, Pageable page) {
        return userRepository.findFriendsByUser(user, page);
    }

    @Override
    public Page<User> getInvitationsByCurrentUser(Pageable page) {
        return userRepository.findInvitationsByUser(getCurrentUser(), page);
    }
}
