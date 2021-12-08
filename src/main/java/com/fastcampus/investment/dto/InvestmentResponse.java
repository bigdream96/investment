package com.fastcampus.investment.dto;

import com.fastcampus.investment.constants.InvestmentStatus;
import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InvestmentResponse {

    private static final List<InvestmentResponse> EMPTY_LIST = new ArrayList<>();

    private Long id;

    private Long userId;

    private Long investedAmount;

    private InvestmentStatus status;

    private LocalDate investedAt;

    @JsonProperty("product")
    private ProductResponse productResponse;

    public static InvestmentResponse entityToResponse(Investment investment) {
        return InvestmentResponse.builder()
                .id(investment.getId())
                .status(investment.getStatus())
                .investedAt(investment.getInvestedAt())
                .investedAmount(investment.getInvestedAmount())
                .productResponse(ProductResponse.toDto(investment.getProduct()))
                .build();
    }



    public static List<InvestmentResponse> entityToResponseList(List<Investment> investments) {
        List<InvestmentResponse> investmentResponses = new ArrayList<>();
        for(Investment investment : investments) {
            InvestmentResponse investmentResponse = InvestmentResponse.builder()
                    .id(investment.getId())
                    .status(investment.getStatus())
                    .investedAt(investment.getInvestedAt())
                    .investedAmount(investment.getInvestedAmount())
                    .productResponse(ProductResponse.toDto(investment.getProduct()))
                    .build();
            investmentResponses.add(investmentResponse);
        }
        return investmentResponses;
    }

    public static List<InvestmentResponse> emptyResponseList() {
        return EMPTY_LIST;
    }
}
