package com.fastcampus.investment.repository;

import com.fastcampus.investment.constants.InvestmentStatus;
import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fastcampus.investment.constants.InvestmentStatus.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InvestmentTest {

    @Autowired
    private InvestmentRepository investmentRepository;

    @Test
    @DisplayName("신규 상품투자 등록")
    void save() {
        Product product = Product.builder()
                .title("You can be elon musk")
                .totalInvestingAmount(10000L)
                .startedAt(LocalDate.now())
                .finishedAt(LocalDate.now())
                .build();
        Investment investment = Investment.builder()
                .product(product)
                .status(INVESTED)
                .investedAmount(10000L)
                .investedAt(LocalDate.now())
                .build();

        Investment saveInvestment = investmentRepository.save(investment);

        assertEquals(investment, saveInvestment);
    }

    @Test
    @DisplayName("상품ID로 상품투자 조회")
    void findByProductId() {
        List<Investment> investmentList = new ArrayList<>();
        Product product = Product.builder()
                .title("You can be elon musk")
                .totalInvestingAmount(10000L)
                .startedAt(LocalDate.now())
                .finishedAt(LocalDate.now())
                .build();
        Investment investment1 = Investment.builder()
                .product(product)
                .status(INVESTED)
                .investedAmount(5000L)
                .investedAt(LocalDate.now())
                .build();
        Investment investment2 = Investment.builder()
                .product(product)
                .status(INVESTED)
                .investedAmount(5000L)
                .investedAt(LocalDate.now())
                .build();
        investmentList.add(investment1);
        investmentList.add(investment2);

        investmentRepository.saveAll(investmentList);
        List<Investment> findInvestmentList = investmentRepository.findByProduct(product).orElse(new ArrayList<>());

        assertEquals(investmentList, findInvestmentList);
    }

    @Test
    @DisplayName("투자ID로 상품투자 조회")
    void findById() {
        Product product = Product.builder()
                .title("You can be elon musk")
                .totalInvestingAmount(10000L)
                .startedAt(LocalDate.now())
                .finishedAt(LocalDate.now())
                .build();
        Investment investment = Investment.builder()
                .product(product)
                .status(INVESTED)
                .investedAmount(10000L)
                .investedAt(LocalDate.now())
                .build();

        investmentRepository.save(investment);
        Optional<Investment> findInvestment = investmentRepository.findById(investment.getId());

        assertEquals(investment, findInvestment.orElse(Investment.builder().build()));
    }

    @Test
    @DisplayName("상품투자 상태변경")
    void update() {
        InvestmentStatus status = INVESTED;
        Product product = Product.builder()
                .title("You can be elon musk")
                .totalInvestingAmount(10000L)
                .startedAt(LocalDate.now())
                .finishedAt(LocalDate.now())
                .build();
        Investment investment = Investment.builder()
                .product(product)
                .status(status)
                .investedAmount(10000L)
                .investedAt(LocalDate.now())
                .build();

        Investment saveInvestment = investmentRepository.save(investment);
        saveInvestment.changeStatus(CANCELED);
        Investment changedInvestment = investmentRepository.save(saveInvestment);

        assertNotEquals(status, changedInvestment.getStatus());
    }
}