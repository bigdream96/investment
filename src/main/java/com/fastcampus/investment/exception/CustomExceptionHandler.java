package com.fastcampus.investment.exception;

import com.fastcampus.investment.constants.ErrorCode;
import com.fastcampus.investment.dto.Message;
import com.fastcampus.investment.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

import static com.fastcampus.investment.constants.ErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Message<String>> handleException(Exception e) {
        logging(e.getMessage(), INTERNAL_SERVER_ERROR, "");
        return new ResponseEntity<>(Message.ERROR(false, INTERNAL_SERVER_ERROR, e.getMessage()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = APIException.class)
    protected ResponseEntity<Message<String>> handleAPIException(APIException ae) {
        HttpStatus httpStatus;
        ErrorCode errorCode = ae.getErrorCode();

        if (errorCode == NO_INVESTMENT_DATA || errorCode == NO_PRODUCT_DATA)
            httpStatus = NOT_FOUND;
        else
            httpStatus = BAD_REQUEST;

        Message<String> message = Message.ERROR(false, httpStatus, ae.getMessage(), ae.getData());
        logging(ae.getMessage(), httpStatus, ae.getData());
        return new ResponseEntity<>(message, httpStatus);
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    protected ResponseEntity<Message<String>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        logging(e.getMessage(), BAD_REQUEST, "");
        Message<String> message = Message.ERROR(false, BAD_REQUEST, "요청 헤더 값이 누락되거나 바인딩할 수 없습니다.");
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logging(e.getMessage(), status, "");
        Message<String> message = Message.ERROR(false, status, "요청 파라미터 값이 누락되거나 잘못된 값입니다.");
        return new ResponseEntity<>(message, status);
    }

    private void logging(String message, HttpStatus status, String data) {
        LogUtil.recordLog("[ 예외발생 ] status=" + status + ", message=" + message + ", data=" + data);
    }
}