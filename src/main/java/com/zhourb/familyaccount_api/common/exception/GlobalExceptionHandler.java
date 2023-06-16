package com.zhourb.familyaccount_api.common.exception;

import com.zhourb.familyaccount_api.utils.http.HttpResult;
import com.zhourb.familyaccount_api.utils.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @author RenQun
 * @version 1.0
 * @date 2022/2/16 13:01
 * @description
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public HttpResult handleBindGetException(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";"));
        return HttpResult.error(HttpStatus.SC_BAD_REQUEST, message);
    }

    @ExceptionHandler(UserExistException.class)
    public HttpResult userExistException(UserExistException exception) {
        return HttpResult.error(HttpStatus.SC_BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(DataExistException.class)
    public HttpResult dataExistException(DataExistException exception) {
        return HttpResult.error(HttpStatus.SC_BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public HttpResult nullException(NullPointerException exception) {
        return HttpResult.error(HttpStatus.SC_BAD_REQUEST, exception.getMessage());
    }

}
