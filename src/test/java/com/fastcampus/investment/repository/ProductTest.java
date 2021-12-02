package com.fastcampus.investment.repository;

import com.fastcampus.investment.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("신규 투자상품 등록")
    void save() {
        Product product = Product.builder()
                .title("You can be elon musk")
                .totalInvestingAmount(10000L)
                .startedAt(LocalDate.now())
                .finishedAt(LocalDate.now())
                .build();

        Product saveProduct = productRepository.save(product);

        assertEquals(product, saveProduct);
    }

    @Test
    @DisplayName("모든 투자상품 조회")
    void findAll() {
        List<Product> productList = new ArrayList<>();
        Product product1 = Product.builder()
                .title("You can be elon musk")
                .totalInvestingAmount(100000000L)
                .startedAt(LocalDate.now())
                .finishedAt(LocalDate.now())
                .build();
        Product product2 = Product.builder()
                .title("You can be elon musk")
                .totalInvestingAmount(600000000L)
                .startedAt(LocalDate.now())
                .finishedAt(LocalDate.now())
                .build();
        productList.add(product1);
        productList.add(product2);

        productRepository.saveAll(productList);
        List<Product> findProductList = productRepository.findAll();

        assertEquals(productList, findProductList);
    }

    @Test
    @DisplayName("상품ID로 투자상품 조회")
    void findById() {
        Product product = Product.builder()
                .title("You can be elon musk")
                .totalInvestingAmount(10000L)
                .startedAt(LocalDate.now())
                .finishedAt(LocalDate.now())
                .build();

        productRepository.save(product);
        Optional<Product> findProduct = productRepository.findById(product.getId());

        assertEquals(product, findProduct.orElse(Product.builder().build()));
    }

    @Test
    @DisplayName("모집기간의 투자상품 조회")
    void inquireInvestableProducts() {
        List<Product> productList = new ArrayList<>();
        Product oldProduct = Product.builder()
                .title("You can be elon musk")
                .totalInvestingAmount(100000000L)
                .startedAt(LocalDate.now().minusDays(6))
                .finishedAt(LocalDate.now().minusDays(3))
                .build();
        Product currentProduct = Product.builder()
                .title("TOBE-RICH of Warren Buffett")
                .totalInvestingAmount(600000000L)
                .startedAt(LocalDate.now().minusDays(3))
                .finishedAt(LocalDate.now().plusDays(3))
                .build();
        productList.add(currentProduct);

        productRepository.save(oldProduct);
        productRepository.save(currentProduct);
        List<Product> findProductList = productRepository.inquireInvestableProducts();

        assertEquals(productList, findProductList);
    }


}