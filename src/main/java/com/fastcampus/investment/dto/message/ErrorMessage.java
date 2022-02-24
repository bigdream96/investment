package com.fastcampus.investment.dto.message;

import lombok.*;

@Builder
@Getter
@ToString
public class ErrorMessage<T> {
    private Boolean isSuccess;
    private T description;
    private Boolean data;

    public static <T> ErrorMessage<T> error(T data) {
        return ErrorMessage.<T>builder()
                .isSuccess(false)
                .description(data)
                .data(false)
                .build();
    }
}
