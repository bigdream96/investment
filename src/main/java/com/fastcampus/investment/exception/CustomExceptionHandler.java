package com.fastcampus.investment.exception;

import com.fastcampus.investment.dto.Message;
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

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Message<String>> handleException(Exception e) {
        log.warn(format("[ 예외발생 ] %s", e.getMessage()));
        return new ResponseEntity<>(Message.error(e.getMessage()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = InvestmentException.class)
    protected ResponseEntity<Message<String>> handleInvestmentException(InvestmentException e) {
        Message<String> message = Message.error(e.getErrorCode().getMessage());
        log.warn(format("[ 예외발생 ] %s, data=%s,", message.getDescription(), e.getData()));
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    protected ResponseEntity<Message<String>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        log.warn(format("[ 예외발생 ] %s", e.getMessage()));
        Message<String> message = Message.error("요청 헤더 값이 누락되거나 바인딩할 수 없습니다.");
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<Message<String>> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn(format("[ 예외발생 ] %s", e.getMessage()));
        Message<String> message = Message.error("유효하지 않은 값입니다.");
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn(format("[ 예외발생 ] %s", e.getMessage()));
        Message<String> message = Message.error("요청 파라미터 값이 누락되거나 잘못된 값입니다.");
        return new ResponseEntity<>(message, status);
    }
}