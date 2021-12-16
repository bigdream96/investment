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

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final ProductRepository productRepository;
    private final InvestmentRepository investmentRepository;

    public List<InvestmentResponse> searchInvestment(Long userId) throws APIException {
        List<Investment> findInvestment = investmentRepository.findByUserId(userId);

        if(findInvestment.isEmpty())
            throw new APIException(NO_INVESTMENT_DATA, "userId : " + userId);

        return entityToResponseList(findInvestment);
    }

    public InvestmentResponse invest(Long userId, Long productId, Long investAmount) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new APIException(NO_PRODUCT_DATA, "userId : " + userId, "productId : " + productId, "investAmount : " + investAmount)
        );

        Long goalAmount = product.getTotalInvestingAmount();
        Long totalInvested = investmentRepository.findByProduct(product).stream().mapToLong(Investment::getInvestedAmount).sum();
        InvestmentStatus investmentStatus = INVESTED;
        if (isNoTotalInvestment(totalInvested, goalAmount)) {
            investmentStatus = FAIL;
        }
        if (!isInvestmentPossible(investAmount, totalInvested, goalAmount)) {
            investmentStatus = FAIL;
        }

        Investment investment = Investment.builder()
                .userId(userId)
                .product(product)
                .investedAmount(investAmount)
                .status(investmentStatus)
                .investedAt(LocalDate.now())
                .build();
        Investment findInvestment = investmentRepository.save(investment);

        return entityToResponse(findInvestment);
    }

    public InvestmentResponse updateInvestment(Long userId, Long investmentId, InvestmentStatus status) {
        Investment investment = investmentRepository.findById(investmentId).orElseThrow(() -> new APIException(NO_INVESTMENT_DATA, "investmentId : " + investmentId));

        if(!Objects.equals(investment.getUserId(), userId))
            throw new APIException(WRONG_INVESTMENT_REQUEST, "userId : " + userId);

        investment.changeStatus(status);
        investmentRepository.save(investment);

        return entityToResponse(investment);
    }

    private boolean isNoTotalInvestment(Long total, Long goalAmount) {
        return total >= goalAmount;
    }

    private boolean isInvestmentPossible(Long investmentAmount, Long totalInvested, Long goalAmount) {
        return investmentAmount <= (goalAmount - totalInvested);
    }

}
