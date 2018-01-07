package net.ignaszak.socialnetwork.controller.rest;

import net.ignaszak.socialnetwork.advice.RestControllerExceptionAdvice;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
abstract public class RestControllerTestInitializer {

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(getTestedController())
            //.setControllerAdvice(new RestControllerExceptionHandler())
            .setHandlerExceptionResolvers(createExceptionResolver())
            .build();
    }

    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(RestControllerExceptionAdvice.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new RestControllerExceptionAdvice(), method);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

    protected abstract Object getTestedController();
}
