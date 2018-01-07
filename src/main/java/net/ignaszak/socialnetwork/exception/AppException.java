package net.ignaszak.socialnetwork.exception;

public abstract class AppException extends Exception {

    public AppException() {
        super("Unknown error");
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Exception cause) {
        super(message, cause);
    }
}
