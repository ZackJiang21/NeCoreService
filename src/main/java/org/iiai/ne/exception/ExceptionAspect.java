package org.iiai.ne.exception;

import org.apache.commons.lang3.StringUtils;
import org.iiai.ne.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ZackJiang on 2018/5/13.
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAspect.class);

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public Response handleSessionRequiredException(UnAuthorizedException e) {
        LOGGER.error("session required");
        return new Response().failure("session required");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotPermittedException.class)
    public Response handleNotPermittedException(NotPermittedException e) {
        LOGGER.error("no right", e);
        return new Response().failure("no right");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpNotFoundException.class)
    public Response handleNotFoundException(HttpNotFoundException e) {
        LOGGER.error("not found", e);
        return new Response().failure("not found");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        LOGGER.error("could_not_read_json...", e);
        return new Response().failure("could_not_read_json");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Response handleValidationException(MethodArgumentNotValidException e) {
        LOGGER.error("parameter_validation_exception...", e);
        return new Response().failure("parameter_validation_exception");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public Response handleBadRequestException(BadRequestException e) {
        LOGGER.error("parameter_validation_exception...", e);
        String msg = StringUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : "parameter_validation_exception";
        return new Response().failure(msg);
    }

    /**
     * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        LOGGER.error("request_method_not_supported...", e);
        return new Response().failure("request_method_not_supported");
    }

    /**
     * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Response handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        LOGGER.error("content_type_not_supported...", e);
        return new Response().failure("content_type_not_supported");
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        LOGGER.error("Internal Server Error...", e);
        return new Response().failure("Internal Server Error");
    }
}
