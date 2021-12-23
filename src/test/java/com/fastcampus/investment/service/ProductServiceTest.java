package com.fastcampus.investment.service;

import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.response.ProductResponse;
import com.fastcampus.investment.repository.InvestmentRepository;
import com.fastcampus.investment.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.fastcampus.investment.constants.InvestmentStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private InvestmentRepository investmentRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("투자 가능한 상품 조회(기간)")
    void searchInvestmentProductList() {
        List<Product> expectedProductList = new ArrayList<>();
        LocalDate staDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 12, 31);
        Product product = Product.builder()
                .id(1L)
                .title("TOBE-RICH of Warren Buffett")
                .totalInvestingAmount(600_000_000L)
                .startedAt(staDate.plusDays(3))
                .finishedAt(endDate.minusDays(3))
                .build();
        expectedProductList.add(product);

        when(productRepository.findCurrentDate()).thenReturn(expectedProductList);
        when(investmentRepository.sumInvestedAmount(product)).thenReturn(0L);
        when(investmentRepository.countByProductAndStatus(product, INVESTED)).thenReturn(0);

        List<ProductResponse> productResponses = productService.searchInvestmentProductList();

        assertAll(
                () -> assertEquals(1, productResponses.size()),
                () -> assertEquals(product.getId(), productResponses.get(0).getId()),
                () -> assertEquals(0L, productResponses.get(0).getInvestedAmount()),
                () -> assertEquals(0, productResponses.get(0).getInvestedCount())
        );
    }
}