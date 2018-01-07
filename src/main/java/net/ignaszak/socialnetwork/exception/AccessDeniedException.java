package net.ignaszak.socialnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends AppException {

    public AccessDeniedException() {
        super("Access denied");
    }
}
