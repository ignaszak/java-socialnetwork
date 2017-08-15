package com.ignaszak.socialnetwork.service.user;

import com.ignaszak.socialnetwork.domain.User;
import com.ignaszak.socialnetwork.form.UserRegistrationForm;
import com.ignaszak.socialnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tomek on 05.04.17.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private SecurityService securityService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmailOrUsername(String find) {
        return userRepository.findUserByEmailOrUsername(find, find);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserFromUserRegistrationForm(UserRegistrationForm userRegistrationForm) {
        User user = new User();
        user.setEmail(userRegistrationForm.getEmail());
        user.setUsername(userRegistrationForm.getUsername());
        user.setPassword(userRegistrationForm.getPassword());
        user.setRole(userRegistrationForm.getRole());
        return user;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findCurrentUser().get(0);
    }

    @Override
    public User getUserByActivationCode(String code) {
        return userRepository.findUserByActivationCode(code);
    }
}
