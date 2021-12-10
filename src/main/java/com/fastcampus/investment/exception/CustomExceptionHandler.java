package com.fastcampus.investment.exception;

import com.fastcampus.investment.constants.ErrorCode;
import com.fastcampus.investment.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

import static com.fastcampus.investment.constants.ErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Message<String>> handleAPIException(RuntimeException re) {
        if(re instanceof APIException) {
            APIException ae = (APIException)re;
            HttpStatus httpStatus;
            ErrorCode errorCode = ae.getErrorCode();

            if(errorCode == NO_INVESTMENT_DATA || errorCode == NO_PRODUCT_DATA) {
                httpStatus = NOT_FOUND;
            } else {
                httpStatus = BAD_REQUEST;
            }

            Message<String> message = Message.ERROR(false, httpStatus, ae.getMessage());
            LOGGER.warn("[ 예외 발생 ] " + httpStatus + ", " + ae.getMessage());
            return new ResponseEntity<>(message, httpStatus);
        } else {
            LOGGER.warn("[ 예외 발생 ] " + BAD_REQUEST + ", " + re.getMessage());
            return new ResponseEntity<>(Message.ERROR(false, BAD_REQUEST, re.getMessage()), BAD_REQUEST);
        }
    }

    @ExceptionHandler(value = MissingRequestValueException.class)
    protected ResponseEntity<Message<String>> handleValidationException(MissingRequestValueException e) {
        LOGGER.warn("[ 예외 발생 ] " + e.getMessage() + " " + e.getMessage());
        Message<String> message = Message.ERROR(false, BAD_REQUEST, "요청 값이 누락되거나 바인딩할 수 없습니다.", e.getMessage());
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

}
