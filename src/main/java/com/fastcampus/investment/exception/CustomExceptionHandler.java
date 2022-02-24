package com.fastcampus.investment.exception;

import com.fastcampus.investment.dto.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static com.fastcampus.investment.constants.ErrorCode.API_SERVER_ERROR;
import static com.fastcampus.investment.constants.ErrorCode.BAD_API_REQUEST;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorMessage<String>> handleException(Exception e) {
        log.warn(format("[ 예외발생 ] %s", e.getMessage()));
        ErrorMessage<String> errorMessage = ErrorMessage.error(API_SERVER_ERROR.getMessage());
        return new ResponseEntity<>(errorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(value = InvestmentException.class)
    protected ResponseEntity<ErrorMessage<String>> handleAuthException(InvestmentException e) {
        log.warn(format("[ 예외발생 ] %s", e.getErrorCode().getMessage()));
        ErrorMessage<String> errorMessage = ErrorMessage.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(errorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    protected ResponseEntity<ErrorMessage<String>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        log.warn(format("[ 예외발생 ] %s", e.getMessage()));
        ErrorMessage<String> errorMessage = ErrorMessage.error(BAD_API_REQUEST.getMessage());
        return new ResponseEntity<>(errorMessage, BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<ErrorMessage<String>> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn(format("[ 예외발생 ] %s", e.getMessage()));
        ErrorMessage<String> errorMessage = ErrorMessage.error(BAD_API_REQUEST.getMessage());
        return new ResponseEntity<>(errorMessage, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn(format("[ 예외발생 ] %s", e.getMessage()));
        ErrorMessage<String> errorMessage = ErrorMessage.error(BAD_API_REQUEST.getMessage());
        return new ResponseEntity<>(errorMessage, BAD_REQUEST);
    }
}
