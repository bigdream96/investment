package com.fastcampus.investment.service;

import com.fastcampus.investment.constants.InvestmentStatus;
import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.response.InvestmentResponse;
import com.fastcampus.investment.exception.InvestmentException;
import com.fastcampus.investment.repository.InvestmentRepository;
import com.fastcampus.investment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.fastcampus.investment.constants.ErrorCode.*;
import static com.fastcampus.investment.constants.InvestmentStatus.FAIL;
import static com.fastcampus.investment.constants.InvestmentStatus.INVESTED;
import static com.fastcampus.investment.dto.response.InvestmentResponse.*;
import static java.lang.String.*;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final ProductRepository productRepository;
    private final InvestmentRepository investmentRepository;

    public List<InvestmentResponse> searchInvestment(Long userId) {
        List<Investment> investments = investmentRepository.findByUserIdOrderByInvestedAtDesc(userId);
        return of(investments);
    }

    public InvestmentResponse invest(Long userId, Long productId, Long investAmount) throws InvestmentException {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new InvestmentException(NO_PRODUCT_DATA, format("productId : %d", productId))
        );

        InvestmentStatus investmentStatus = checkInvestmentStatus(product, investAmount);

        Investment investment = Investment.builder()
                .userId(userId)
                .product(product)
                .investedAmount(investAmount)
                .status(investmentStatus)
                .investedAt(LocalDateTime.now())
                .build();
        Investment saveInvestment = investmentRepository.save(investment);

        return of(saveInvestment);
    }

    public InvestmentResponse updateInvestment(Long userId, Long investmentId, InvestmentStatus status) throws InvestmentException {
        Investment investment = investmentRepository.findById(investmentId).orElseThrow(
                () -> new InvestmentException(NO_INVESTMENT_DATA, format("investmentId : %d", investmentId))
        );

        if (!Objects.equals(investment.getUserId(), userId))
            throw new InvestmentException(NOT_MATCH_USER_ID_IN_INVESTMENT, format("userId : %d", userId));

        investment.changeStatus(status);
        Investment updateInvestment = investmentRepository.save(investment);

        return of(updateInvestment);
    }

    private InvestmentStatus checkInvestmentStatus(Product product, Long investAmount) {
        Long goalAmount = product.getTotalInvestingAmount();
        Long totalInvested = investmentRepository.sumInvestedAmount(product);
        return investAmount <= (goalAmount - totalInvested) ? INVESTED : FAIL;
    }

}
