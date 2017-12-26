package net.ignaszak.socialnetwork.dto;

public class ErrorDTO {

    private String message;

    private ErrorDTO(String message) {
        this.message = message;
    }

    public static ErrorDTO notFound() {
        return new ErrorDTO("Not found");
    }

    public String getMessage() {
        return message;
    }
}
