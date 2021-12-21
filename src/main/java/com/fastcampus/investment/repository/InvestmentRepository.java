package com.fastcampus.investment.repository;

import com.fastcampus.investment.constants.InvestmentStatus;
import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByProduct(Product product);
    List<Investment> findByUserId(Long userId);
    Integer countByProductAndStatus(Product product, InvestmentStatus status);
    @Query("SELECT COALESCE(SUM(t.investedAmount), 0) FROM Investment t WHERE t.product.id = :#{#product.id} AND t.status = 'INVESTED'")
    Long sumInvestedAmount(Product product);
}
