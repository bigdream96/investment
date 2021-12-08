package com.fastcampus.investment.domain;

import com.fastcampus.investment.constants.InvestmentStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "INVESTMENTS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Investment {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private Long investedAmount;

    @Column(nullable = false)
    private InvestmentStatus status;

    @Column(nullable = false)
    private LocalDate investedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCTS_ID")
    private Product product;

    public void changeStatus(InvestmentStatus status) {
        this.status = status;
    }
}
