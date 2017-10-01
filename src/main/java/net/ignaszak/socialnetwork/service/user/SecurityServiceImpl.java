package net.ignaszak.socialnetwork.service.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Created by tomek on 10.04.17.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public User findLoggedInUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User) {
            return (User) user;
        }
        return null;
    }
}
