package com.fastcampus.investment.repository;

import com.fastcampus.investment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE current_timestamp between p.startedAt and p.finishedAt")
    List<Product> inquireInvestableProducts();
}
