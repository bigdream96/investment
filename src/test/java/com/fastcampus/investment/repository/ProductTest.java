package com.fastcampus.investment.repository;

import com.fastcampus.investment.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("신규 투자상품 등록")
    void testSave() {
        Product product = Product.builder()
                .title("test")
                .totalInvestingAmount(10000L)
                .startedAt(LocalDate.now())
                .finishedAt(LocalDate.now())
                .build();

        Product saveProduct = productRepository.save(product);

        assertEquals(product, saveProduct);
    }

    @Test
    @DisplayName("상품ID로 투자상품 조회")
    void testFindById() {
        Long productId = 1L;

        Optional<Product> findProduct = productRepository.findById(productId);

        assertEquals(productId, findProduct.orElse(Product.builder().build()).getId());
    }
}