package net.ignaszak.socialnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRelationException extends AppException {

    public InvalidRelationException() {
        super("Could not add relation");
    }
}
