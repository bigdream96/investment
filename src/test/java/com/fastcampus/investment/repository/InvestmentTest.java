package com.fastcampus.investment.repository;

import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.fastcampus.investment.constants.InvestmentStatus.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InvestmentTest {

    @Autowired
    private InvestmentRepository investmentRepository;

    private Product product;

    @BeforeEach
    void init() {
        product = Product.builder()
                .title("test")
                .totalInvestingAmount(10000L)
                .startedAt(LocalDate.now())
                .finishedAt(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("신규 상품투자 등록")
    void testSave() {
        Investment investment = Investment.builder()
                .product(product)
                .status(INVESTED)
                .investedAmount(10000L)
                .investedAt(LocalDateTime.now())
                .build();

        Investment saveInvestment = investmentRepository.save(investment);

        assertEquals(investment, saveInvestment);
    }

    @Test
    @DisplayName("투자ID로 상품투자 조회")
    void testFindById() {
        Investment investment = Investment.builder()
                .product(product)
                .status(INVESTED)
                .investedAmount(10000L)
                .investedAt(LocalDateTime.now())
                .build();

        investmentRepository.save(investment);
        Optional<Investment> findInvestment = investmentRepository.findById(investment.getId());

        assertEquals(investment, findInvestment.orElse(Investment.builder().build()));
    }

    @Test
    @DisplayName("상품투자 상태변경")
    void testUpdate() {
        Investment investment = Investment.builder()
                .product(product)
                .status(INVESTED)
                .investedAmount(10000L)
                .investedAt(LocalDateTime.now())
                .build();

        Investment saveInvestment = investmentRepository.save(investment);
        saveInvestment.changeStatus(CANCELED);
        Investment changedInvestment = investmentRepository.save(saveInvestment);

        assertEquals(saveInvestment.getStatus(), changedInvestment.getStatus());
    }
}