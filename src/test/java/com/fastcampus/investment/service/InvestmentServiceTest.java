package com.fastcampus.investment.service;

import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.response.InvestmentResponse;
import com.fastcampus.investment.repository.InvestmentRepository;
import com.fastcampus.investment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.fastcampus.investment.constants.InvestmentStatus.CANCELED;
import static com.fastcampus.investment.constants.InvestmentStatus.INVESTED;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvestmentServiceTest {
    @Mock
    private InvestmentRepository investmentRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
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

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(investmentRepository.save(any(Investment.class))).then(returnsFirstArg());

        InvestmentResponse response = investmentService.invest(userId, product.getId(), investAmount);

        assertAll(
                () -> assertEquals(userId, response.getUserId()),
                () -> assertEquals(product.getId(), response.getProductResponse().getId()),
                () -> assertEquals(INVESTED, response.getStatus())
        );

    }

    @Test
    @DisplayName("투자조회")
    void searchInvestment() {
        Long userId = 1L;
        Long investAmount = 10000L;
        Investment investment = Investment.builder()
                .product(product)
                .userId(userId)
                .investedAt(LocalDateTime.now())
                .investedAmount(investAmount)
                .status(INVESTED)
                .build();
        List<Investment> expectedInvestments = List.of(investment);

        when(investmentRepository.findByUserIdOrderByInvestedAtDesc(userId)).thenReturn(expectedInvestments);

        List<InvestmentResponse> responses = investmentService.searchInvestment(userId);

        assertAll(
                () -> assertEquals(expectedInvestments.size(), responses.size()),
                () -> assertEquals(userId, responses.get(0).getUserId()),
                () -> assertEquals(product.getId(), responses.get(0).getProductResponse().getId()),
                () -> assertEquals(INVESTED, responses.get(0).getStatus())
        );
    }

    @Test
    @DisplayName("투자취소")
    void cancelInvestment() {
        Long id = 1L;
        Long userId = 1L;
        Investment investment = Investment.builder()
                .product(product)
                .userId(userId)
                .investedAt(LocalDateTime.now())
                .status(CANCELED)
                .build();

        when(investmentRepository.findById(id)).thenReturn(Optional.of(investment));
        when(investmentRepository.save(any(Investment.class))).then(returnsFirstArg());

        InvestmentResponse response = investmentService.updateInvestment(userId, product.getId(), CANCELED);

        assertAll(
                () -> assertEquals(userId, response.getUserId()),
                () -> assertEquals(product.getId(), response.getProductResponse().getId()),
                () -> assertEquals(CANCELED, response.getStatus())
        );
    }
}