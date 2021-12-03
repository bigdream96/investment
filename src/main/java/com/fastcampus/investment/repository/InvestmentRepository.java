package com.fastcampus.investment.repository;

import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByProductId(Long id);
    List<Investment> findByProduct(Product product);
}
