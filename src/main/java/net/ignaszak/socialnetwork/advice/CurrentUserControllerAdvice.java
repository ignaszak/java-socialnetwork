package net.ignaszak.socialnetwork.advice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
class CurrentUserControllerAdvice {

    @ModelAttribute("currentUser")
    public User getCurrentUser(Authentication authentication) {
        return authentication == null ? null : (User) authentication.getPrincipal();
    }
}
