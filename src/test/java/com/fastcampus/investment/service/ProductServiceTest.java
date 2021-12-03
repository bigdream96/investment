package com.fastcampus.investment.service;

import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.ProductResponse;
import com.fastcampus.investment.repository.InvestmentRepository;
import com.fastcampus.investment.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
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
        oldProduct = Product.builder()
                .id(1L)
                .title("You can be elon musk")
                .totalInvestingAmount(100000000L)
                .startedAt(LocalDate.now().minusDays(6))
                .finishedAt(LocalDate.now().minusDays(3))
                .build();
        currentProduct = Product.builder()
                .id(2L)
                .title("TOBE-RICH of Warren Buffett")
                .totalInvestingAmount(600000000L)
                .startedAt(LocalDate.now().minusDays(3))
                .finishedAt(LocalDate.now().plusDays(3))
                .build();
        productList.add(oldProduct);
        productList.add(currentProduct);

        when(productRepository.findAll()).thenReturn(productList);
    }

    @Test
    @DisplayName("투자 가능한 상품 조회(기간)")
    void inquireInvestableProducts() {
        List<ProductResponse> productList = productService.inquireInvestableProducts();

        Assertions.assertEquals(1, productList.size());
        Assertions.assertEquals(currentProduct.getId(), productList.get(0).getId());
        Assertions.assertNotEquals(oldProduct.getId(), productList.get(0).getId());
    }
}