package net.ignaszak.socialnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public final class MediaUploadException extends AppException {

    public MediaUploadException() {
        super("Media upload error");
    }
    public MediaUploadException(String message) {
        super(message);
    }
    public MediaUploadException(String message, Exception cause) {
        super(message, cause);
    }
}
