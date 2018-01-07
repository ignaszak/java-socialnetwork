package net.ignaszak.socialnetwork.dto;

public class ErrorRestDTO {

    private boolean success = false;

    private String message;

    public ErrorRestDTO(String message) {
        this.message = message;
    }

    public static ErrorRestDTO notFound() {
        return new ErrorRestDTO("Not found");
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
