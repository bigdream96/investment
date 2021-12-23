package com.fastcampus.investment.repository;

import com.fastcampus.investment.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE current_date BETWEEN p.startedAt AND p.finishedAt")
    List<Product> findCurrentDate();

}
