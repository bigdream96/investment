package com.fastcampus.investment.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * E0XXXX : 서버 오류
 * E1XXXX : 클라이언트 요청이 잘못된 경우
 */

@Getter
@AllArgsConstructor
public enum ErrorCode {

    API_SERVER_ERROR("E0000", "서버에서 시스템 에러가 발생하였습니다."),

    BAD_API_REQUEST("E1000", "잘못된 API 요청입니다."),
    NOT_FOUND_API("E1001", "존재하지 않는 API입니다."),
    NO_PRODUCT_DATA("E1002", "모집기간 내 투자상품이 존재하지 않습니다."),
    NO_INVESTMENT_DATA("E1003", "투자 내역이 존재하지 않습니다."),
    NOT_MATCH_USER_ID_IN_INVESTMENT("E1004", "투자 내역의 유저ID와 일치하지 않습니다.");

    private final String code;
    private final String message;
}
