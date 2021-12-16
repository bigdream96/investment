package com.fastcampus.investment.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PRODUCTS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long totalInvestingAmount;

    @Column(nullable = false)
    private LocalDate startedAt;

    @Column(nullable = false)
    private LocalDate finishedAt;
}
