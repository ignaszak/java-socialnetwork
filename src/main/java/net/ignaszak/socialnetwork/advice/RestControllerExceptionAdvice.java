package net.ignaszak.socialnetwork.advice;

import net.ignaszak.socialnetwork.dto.ErrorRestDTO;
import net.ignaszak.socialnetwork.exception.AppException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionAdvice extends ControllerExceptionAdvice {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorRestDTO> handleRestException(AppException e) {
        return getErrorResponseEntity(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRestDTO handleValidationRestException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .findFirst()
            .orElse(e.getMessage());
        return new ErrorRestDTO(message);
    }
}
