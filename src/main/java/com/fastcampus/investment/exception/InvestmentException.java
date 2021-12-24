package com.fastcampus.investment.exception;

import com.fastcampus.investment.constants.ErrorCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class InvestmentException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String data;

    public InvestmentException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = "";
    }

    public InvestmentException(ErrorCode errorCode, String... data) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = Arrays.toString(data);
    }
}
