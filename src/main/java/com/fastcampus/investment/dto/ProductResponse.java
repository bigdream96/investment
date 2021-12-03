package com.fastcampus.investment.dto;

import com.fastcampus.investment.domain.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductResponse {
    private Long id;

    private String title;

    private Long totalInvestAmount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startedAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishedAt;

    private Long investedAmount;

    private Integer investedCount;

    public static ProductResponse toDto(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .totalInvestAmount(product.getTotalInvestingAmount())
                .startedAt(product.getStartedAt())
                .finishedAt(product.getFinishedAt())
                .build();
    }
}
