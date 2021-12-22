package com.fastcampus.investment.service;

import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.response.InvestmentResponse;
import com.fastcampus.investment.dto.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fastcampus.investment.constants.InvestmentStatus.CANCELED;
import static com.fastcampus.investment.constants.InvestmentStatus.INVESTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvestmentServiceTest {
    @Mock
    private InvestmentService investmentService;

    private Product product;

    @BeforeEach
    void init() {
        product = Product.builder()
                .id(1L)
                .title("TOBE-RICH of Warren Buffett")
                .totalInvestingAmount(100000000L)
                .startedAt(LocalDate.now().minusDays(3))
                .finishedAt(LocalDate.now().plusDays(3))
                .build();
    }

    @Test
    @DisplayName("투자하기")
    void invest() {
        Long userId = 1L;
        Long investAmount = 10000L;

        InvestmentResponse expectedResponse = InvestmentResponse.builder()
                .productResponse(ProductResponse.entityToResponse(product))
                .investedAmount(investAmount)
                .status(INVESTED)
                .investedAt(LocalDateTime.now())
                .build();

        when(investmentService.invest(userId, product.getId(), investAmount)).thenReturn(expectedResponse);

        InvestmentResponse response = investmentService.invest(userId, product.getId(), investAmount);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getUserId(), response.getUserId());
        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.getInvestedAmount(), response.getInvestedAmount());
        assertEquals(expectedResponse.getInvestedAt(), response.getInvestedAt());
    }

    @Test
    @DisplayName("투자조회")
    void search() {
        Long userId = 1L;
        Long investAmount = 10000L;

        List<InvestmentResponse> expectedResponses = new ArrayList<>();
        InvestmentResponse expectedResponse = InvestmentResponse.builder()
                .productResponse(ProductResponse.entityToResponse(product))
                .investedAmount(investAmount)
                .status(INVESTED)
                .investedAt(LocalDateTime.now())
                .build();
        expectedResponses.add(expectedResponse);

        when(investmentService.searchInvestment(userId)).thenReturn(expectedResponses);

        List<InvestmentResponse> responses = investmentService.searchInvestment(userId);

        assertEquals(expectedResponses.get(0).getId(), responses.get(0).getId());
        assertEquals(expectedResponses.get(0).getUserId(), responses.get(0).getUserId());
        assertEquals(expectedResponses.get(0).getStatus(), responses.get(0).getStatus());
        assertEquals(expectedResponses.get(0).getInvestedAmount(), responses.get(0).getInvestedAmount());
        assertEquals(expectedResponses.get(0).getInvestedAt(), responses.get(0).getInvestedAt());
    }

    @Test
    @DisplayName("투자취소")
    void cancel() {
        Long userId = 1L;
        Long investAmount = 10000L;

        InvestmentResponse expectedResponse = InvestmentResponse.builder()
                .productResponse(ProductResponse.entityToResponse(product))
                .investedAmount(investAmount)
                .status(CANCELED)
                .investedAt(LocalDateTime.now())
                .build();

        when(investmentService.updateInvestment(userId, product.getId(), CANCELED)).thenReturn(expectedResponse);

        InvestmentResponse response = investmentService.updateInvestment(userId, product.getId(), CANCELED);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getUserId(), response.getUserId());
        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.getInvestedAmount(), response.getInvestedAmount());
        assertEquals(expectedResponse.getInvestedAt(), response.getInvestedAt());
    }
}