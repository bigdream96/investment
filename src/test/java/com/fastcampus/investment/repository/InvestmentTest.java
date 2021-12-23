package com.fastcampus.investment.repository;

import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
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
                .id(1L)
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

    @Test
    @DisplayName("유저ID로 상품투자 조회")
    void testFindByUserIdOrderByInvestedAtDesc() {
        Long userId = 1L;
        Investment investment = Investment.builder()
                .product(product)
                .userId(userId)
                .status(INVESTED)
                .investedAmount(10000L)
                .investedAt(LocalDateTime.now())
                .build();
        investmentRepository.save(investment);

        List<Investment> investments = investmentRepository.findByUserIdOrderByInvestedAtDesc(userId);

        investments.forEach(findInvestment -> assertEquals(1L, findInvestment.getUserId()));
    }

    @Test
    @DisplayName("특정 투자상품 누적투자수")
    void testCountByProductAndStatus() {
        Investment investment = Investment.builder()
                .product(product)
                .status(INVESTED)
                .investedAmount(10000L)
                .investedAt(LocalDateTime.now())
                .build();
        investmentRepository.save(investment);

        Integer count = investmentRepository.countByProductAndStatus(product, INVESTED);

        assertEquals(1, count);
    }

    @Test
    @DisplayName("특정 투자항품 누적투자액")
    void testSumInvestedAmount() {
        Investment investment = Investment.builder()
                .product(product)
                .status(INVESTED)
                .investedAmount(10000L)
                .investedAt(LocalDateTime.now())
                .build();
        investmentRepository.save(investment);

        Long total = investmentRepository.sumInvestedAmount(product);

        assertEquals(10000L, total);
    }
}