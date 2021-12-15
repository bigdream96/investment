package com.fastcampus.investment.exception;

import com.fastcampus.investment.constants.ErrorCode;
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

import static com.fastcampus.investment.constants.ErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Message<String>> handleException(Exception e) {
        log.warn("[ 예외발생 ] " + e.getMessage());
        return new ResponseEntity<>(Message.ERROR(INTERNAL_SERVER_ERROR, e.getMessage()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = APIException.class)
    protected ResponseEntity<Message<String>> handleAPIException(APIException ae) {
        HttpStatus httpStatus;
        ErrorCode errorCode = ae.getErrorCode();

        if (errorCode == NO_INVESTMENT_DATA || errorCode == NO_PRODUCT_DATA)
            httpStatus = NOT_FOUND;
        else
            httpStatus = BAD_REQUEST;

        Message<String> message = Message.ERROR(httpStatus, ae.getMessage(), ae.getData());
        log.warn("[ 예외발생 ] " + ae.getMessage() + ", data=" + ae.getData());
        return new ResponseEntity<>(message, httpStatus);
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    protected ResponseEntity<Message<String>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        log.warn("[ 예외발생 ] " + e.getMessage());
        Message<String> message = Message.ERROR(BAD_REQUEST, "요청 헤더 값이 누락되거나 바인딩할 수 없습니다.");
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("[ 예외발생 ] " + e.getMessage());
        Message<String> message = Message.ERROR(status, "요청 파라미터 값이 누락되거나 잘못된 값입니다.");
        return new ResponseEntity<>(message, status);
    }
}