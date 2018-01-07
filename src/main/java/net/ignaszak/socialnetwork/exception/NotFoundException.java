package net.ignaszak.socialnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public final class NotFoundException extends AppException {

    public NotFoundException() {
        super("Not found");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}
