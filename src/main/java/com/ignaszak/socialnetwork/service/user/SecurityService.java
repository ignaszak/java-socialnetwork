package com.ignaszak.socialnetwork.service.user;

import org.springframework.security.core.userdetails.User;

/**
 * Created by tomek on 10.04.17.
 */
public interface SecurityService {
    User findLoggedInUser();
}
