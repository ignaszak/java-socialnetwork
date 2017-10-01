package com.ignaszak.socialnetwork.service.user;

import com.ignaszak.socialnetwork.domain.User;
import com.ignaszak.socialnetwork.form.UserRegistrationForm;
import com.ignaszak.socialnetwork.repository.UserRepository;
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
    public User add(User user) {
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
    public User getUserByActivationCode(String code) {
        return userRepository.findUserByActivationCode(code);
    }

    @Override
    public User getUserByEmailOrNewEmail(String email) {
        return userRepository.findUserByEmailOrNewEmail(email, email);
    }
}
