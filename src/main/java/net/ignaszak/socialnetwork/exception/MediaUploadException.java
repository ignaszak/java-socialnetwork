package net.ignaszak.socialnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public final class MediaUploadException extends RuntimeException {

    public MediaUploadException() {}
    public MediaUploadException(String message) {
        super(message);
    }
    public MediaUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
