package com.fastcampus.investment.repository;

import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByProduct(Product product);
    List<Investment> findByUserId(Long userId);
    Optional<Integer> countByProduct(Product product);
}
