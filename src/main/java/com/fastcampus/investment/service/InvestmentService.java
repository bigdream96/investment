package com.fastcampus.investment.service;

import com.fastcampus.investment.constants.InvestmentStatus;
import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.response.InvestmentResponse;
import com.fastcampus.investment.exception.APIException;
import com.fastcampus.investment.repository.InvestmentRepository;
import com.fastcampus.investment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        List<Investment> investments = investmentRepository.findByUserId(userId);
        return entityToResponse(investments);
    }

    public InvestmentResponse invest(Long userId, Long productId, Long investAmount) throws APIException {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new APIException(NO_PRODUCT_DATA, format("productId : %d", productId))
        );

        Long goalAmount = product.getTotalInvestingAmount();
        Long totalInvested = investmentRepository.sumInvestedAmount(product);
        InvestmentStatus investmentStatus = INVESTED;

        if (isNoTotalInvestment(totalInvested, goalAmount))
            investmentStatus = FAIL;
        if (isInvestmentImpossible(investAmount, goalAmount, totalInvested))
            investmentStatus = FAIL;

        Investment investment = Investment.builder()
                .userId(userId)
                .product(product)
                .investedAmount(investAmount)
                .status(investmentStatus)
                .investedAt(LocalDate.now())
                .build();
        Investment saveInvestment = investmentRepository.save(investment);

        return entityToResponse(saveInvestment);
    }

    public InvestmentResponse updateInvestment(Long userId, Long investmentId, InvestmentStatus status) throws APIException {
        Investment investment = investmentRepository.findById(investmentId).orElseThrow(
                () -> new APIException(NO_INVESTMENT_DATA, format("investmentId : %d", investmentId))
        );

        if(!Objects.equals(investment.getUserId(), userId))
            throw new APIException(NOT_MATCH_USER_ID_IN_INVESTMENT, format("userId : %d", userId));

        investment.changeStatus(status);
        Investment updateInvestment = investmentRepository.save(investment);

        return entityToResponse(updateInvestment);
    }

    private boolean isNoTotalInvestment(Long total, Long goalAmount) {
        return total >= goalAmount;
    }

    private boolean isInvestmentImpossible(Long investmentAmount, Long goalAmount, Long totalInvested) {
        return investmentAmount >= (goalAmount - totalInvested);
    }

}
