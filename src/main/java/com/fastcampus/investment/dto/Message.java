package com.fastcampus.investment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message<T> {
    // api통신시간
    private LocalDateTime transactionTime;

    // api응답코드
    private HttpStatus resultCode;

    // api성공여부
    private Boolean isSuccess;

    // api부가설명
    private String description;

    // 데이터
    @JsonProperty("data")
    private T data;

    // OK
    public static <T> Message<T> OK(Boolean isSuccess) {
        return (Message<T>)Message.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(OK)
                .isSuccess(isSuccess)
                .description("Success!!!")
                .build();
    }

    // DATA OK
    public static <T> Message<T> OK(T data) {
        return (Message<T>)Message.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(OK)
                .isSuccess(true)
                .description("Success!!!")
                .data(data)
                .build();
    }

    // ERROR
    public static <T> Message<T> ERROR(Boolean isSuccess, HttpStatus status, String description) {
        return (Message<T>)Message.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(status)
                .isSuccess(isSuccess)
                .description(description)
                .build();
    }

    // ERROR
    public static <T> Message<T> ERROR(Boolean isSuccess, HttpStatus status, String description, String data) {
        return (Message<T>)Message.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(status)
                .isSuccess(isSuccess)
                .description(description)
                .data(data)
                .build();
    }
}
