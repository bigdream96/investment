package com.fastcampus.investment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@ToString
public class Message<T> {
    private Boolean isSuccess;
    private String description;
    @JsonProperty("data")
    private T data;

    public static <T> Message<T> ok(T data) {
        return Message.<T>builder()
                .isSuccess(true)
                .description("Success!!!")
                .data(data)
                .build();
    }

    public static <T> Message<T> error(String description) {
        return Message.<T>builder()
                .isSuccess(false)
                .description(description)
                .build();
    }
}
