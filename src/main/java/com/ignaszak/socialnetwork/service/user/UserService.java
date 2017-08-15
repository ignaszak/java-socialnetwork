package com.ignaszak.socialnetwork.service.user;

import com.ignaszak.socialnetwork.domain.User;
import com.ignaszak.socialnetwork.form.UserRegistrationForm;

/**
 * Created by tomek on 05.04.17.
 */
public interface UserService {
    Iterable<User> findAll();
    User findUserByEmailOrUsername(String find);
    User getUserById(Integer id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User createUser(User user);
    User getUserFromUserRegistrationForm(UserRegistrationForm userRegistrationForm);
    User saveUser(User user);
    void deleteUser(User user);
    User getCurrentUser();
    User getUserByActivationCode(String code);
}
