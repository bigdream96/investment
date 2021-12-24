package com.fastcampus.investment.service;

import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.response.InvestmentResponse;
import com.fastcampus.investment.exception.APIException;
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
import java.util.List;
import java.util.Optional;

import static com.fastcampus.investment.constants.ErrorCode.*;
import static com.fastcampus.investment.constants.InvestmentStatus.*;
import static org.junit.jupiter.api.Assertions.*;
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
                .totalInvestingAmount(100_000_000L)
                .startedAt(LocalDate.of(2021, 1, 1).minusDays(3))
                .finishedAt(LocalDate.of(2021, 12, 31).plusDays(3))
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
    @DisplayName("잘못된 투자 CASE1. 찾을 수 없는 투자상품")
    void failInvestmentByNotFoundProduct() {
        Long userId = 1L;
        Long investAmount = 10000L;

        when(productRepository.findById(product.getId())).thenThrow(new APIException(NO_PRODUCT_DATA));

        assertThrows(APIException.class, () -> investmentService.invest(userId, product.getId(), investAmount));
    }

    @Test
    @DisplayName("잘못된 투자 CASE2. 투자할 수 없는 금액")
    void failInvestmentByNotValidData() {
        Long userId = 1L;
        Long investAmount = 1_000_000_000L;

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(investmentRepository.save(any(Investment.class))).then(returnsFirstArg());

        InvestmentResponse response = investmentService.invest(userId, product.getId(), investAmount);

        assertAll(
                () -> assertEquals(FAIL, response.getStatus())
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

    @Test
    @DisplayName("투자취소 실패 CASE1. 찾을 수 없는 투자내역")
    void failCancelInvestmentByNotFoundInvestment() {
        Long userId = 1L;
        Long investmentId = 999L;

        when(investmentRepository.findById(investmentId)).thenThrow(new APIException(NO_INVESTMENT_DATA));

        assertThrows(APIException.class, () -> investmentService.updateInvestment(userId, investmentId, CANCELED));
    }

    @Test
    @DisplayName("투자취소 실패 CASE2. 일치하지 않는 유저ID")
    void failCancelInvestmentByNotMatchUserId() {
        Long investmentId = 999L;
        Investment investment = Investment.builder()
                .id(investmentId)
                .product(product)
                .userId(1L)
                .status(CANCELED)
                .build();

        when(investmentRepository.findById(investmentId)).thenReturn(Optional.of(investment));

        assertThrows(APIException.class, () -> investmentService.updateInvestment(2L, investmentId, CANCELED));
    }
}