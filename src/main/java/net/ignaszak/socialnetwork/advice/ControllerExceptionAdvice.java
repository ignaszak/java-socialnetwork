package net.ignaszak.socialnetwork.advice;

import net.ignaszak.socialnetwork.dto.ErrorRestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;


abstract class ControllerExceptionAdvice {

    HttpStatus getHttpStatus(Exception e) {
        ResponseStatus annotation = findMergedAnnotation(e.getClass(), ResponseStatus.class);
        if (annotation != null) return annotation.value();

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    ResponseEntity<ErrorRestDTO> getErrorResponseEntity(Exception e) {
        return new ResponseEntity<ErrorRestDTO>(new ErrorRestDTO(e.getMessage()), getHttpStatus(e));
    }
}
