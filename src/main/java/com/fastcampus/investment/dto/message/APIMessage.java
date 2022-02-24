package com.fastcampus.investment.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@ToString
public class APIMessage<T> {
    private Boolean isSuccess;
    private String description;
    @JsonProperty("data")
    private T data;

    public static <T> APIMessage<T> ok(T data) {
        return APIMessage.<T>builder()
                .isSuccess(true)
                .description("Success!!!")
                .data(data)
                .build();
    }
}
