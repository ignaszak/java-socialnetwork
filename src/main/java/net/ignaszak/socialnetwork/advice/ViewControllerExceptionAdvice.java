package net.ignaszak.socialnetwork.advice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(annotations = Controller.class)
public class ViewControllerExceptionAdvice extends ControllerExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e, HttpServletResponse response) {
        response.setStatus(getHttpStatus(e).value());
        return new ModelAndView("error", "error", e.getMessage());
    }
}
