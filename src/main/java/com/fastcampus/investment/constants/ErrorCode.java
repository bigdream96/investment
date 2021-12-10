package com.fastcampus.investment.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NO_PRODUCT_DATA("모집기간 내 투자상품이 존재하지 않습니다."),
    NO_INVESTMENT_DATA("투자 내역이 존재하지 않습니다."),
    WRONG_INVESTMENT_REQUEST("잘못된 투자 요청입니다.");

    private final String message;
}
