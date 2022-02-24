package com.fastcampus.investment.domain;

import com.fastcampus.investment.constants.InvestmentStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "INVESTMENTS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long investedAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InvestmentStatus status;

    @Column(updatable = false, nullable = false)
    private LocalDateTime investedAt;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PRODUCTS_ID")
    private Product product;

    public void changeStatus(InvestmentStatus status) {
        this.status = status;
    }
}
