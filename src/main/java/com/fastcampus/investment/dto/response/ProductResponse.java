package com.fastcampus.investment.dto.response;

import com.fastcampus.investment.domain.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
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

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .totalInvestAmount(product.getTotalInvestingAmount())
                .startedAt(product.getStartedAt())
                .finishedAt(product.getFinishedAt())
                .build();
    }

    public void setTotalStatistics(Long investedAmount, Integer investedCount) {
        this.investedAmount = investedAmount;
        this.investedCount = investedCount;
    }
}
