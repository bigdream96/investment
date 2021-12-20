package com.fastcampus.investment.service;

import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.response.ProductResponse;
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
import java.util.ArrayList;
import java.util.List;

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

    private Product oldProduct;
    private Product currentProduct;

    @BeforeEach
    void init() {
        List<Product> productList = new ArrayList<>();
        LocalDate staDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 12, 31);

        oldProduct = Product.builder()
                .id(1L)
                .title("You can be elon musk")
                .totalInvestingAmount(100_000_000L)
                .startedAt(staDate.minusDays(6))
                .finishedAt(staDate.minusDays(3))
                .build();
        currentProduct = Product.builder()
                .id(2L)
                .title("TOBE-RICH of Warren Buffett")
                .totalInvestingAmount(600_000_000L)
                .startedAt(staDate.plusDays(3))
                .finishedAt(endDate.minusDays(3))
                .build();
        productList.add(currentProduct);

        when(productRepository.findCurrentDate()).thenReturn(productList);
    }

    @Test
    @DisplayName("투자 가능한 상품 조회(기간)")
    void searchInvestmentProductList() {
        List<ProductResponse> productList = productService.searchInvestmentProductList();

        assertAll(
                () -> assertEquals(1, productList.size()),
                () -> assertEquals(currentProduct.getId(), productList.get(0).getId()),
                () -> assertNotEquals(oldProduct.getId(), productList.get(0).getId())
        );
    }
}