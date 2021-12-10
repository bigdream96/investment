package com.fastcampus.investment.exception;

import com.fastcampus.investment.constants.ErrorCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class APIException extends RuntimeException {

    private final ErrorCode errorCode;

    public APIException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public APIException(ErrorCode errorCode, String... data) {
        super(errorCode.getMessage() + " " + Arrays.toString(data));
        this.errorCode = errorCode;
    }
}
