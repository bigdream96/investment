package com.fastcampus.investment.dto.response;

import com.fastcampus.investment.constants.InvestmentStatus;
import com.fastcampus.investment.domain.Investment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InvestmentResponse {

    private Long id;

    private Long userId;

    private Long investedAmount;

    private InvestmentStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime investedAt;

    @JsonProperty("product")
    private ProductResponse productResponse;

    public static InvestmentResponse entityToResponse(Investment investment) {
        return InvestmentResponse.builder()
                .id(investment.getId())
                .userId(investment.getUserId())
                .status(investment.getStatus())
                .investedAt(investment.getInvestedAt())
                .investedAmount(investment.getInvestedAmount())
                .productResponse(ProductResponse.entityToResponse(investment.getProduct()))
                .build();
    }

    public static List<InvestmentResponse> entityToResponse(List<Investment> investments) {
        List<InvestmentResponse> investmentResponses = new ArrayList<>();
        for (Investment investment : investments) {
            InvestmentResponse investmentResponse = entityToResponse(investment);
            investmentResponses.add(investmentResponse);
        }
        return investmentResponses;
    }
}
