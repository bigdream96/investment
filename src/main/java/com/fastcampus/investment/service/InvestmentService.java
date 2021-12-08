package com.fastcampus.investment.service;

import com.fastcampus.investment.constants.InvestmentStatus;
import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.InvestmentResponse;
import com.fastcampus.investment.repository.InvestmentRepository;
import com.fastcampus.investment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.fastcampus.investment.constants.InvestmentStatus.FAIL;
import static com.fastcampus.investment.constants.InvestmentStatus.INVESTED;
import static com.fastcampus.investment.dto.InvestmentResponse.*;
import static com.fastcampus.investment.service.ProductService.getEmptyProduct;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final ProductRepository productRepository;
    private final InvestmentRepository investmentRepository;

    public List<InvestmentResponse> searchInvestment(Long userId) {
        Optional<List<Investment>> findInvestment = investmentRepository.findByUserId(userId);

        return findInvestment.map(InvestmentResponse::entityToResponseList).orElseGet(InvestmentResponse::emptyResponseList);
    }

    public List<InvestmentResponse> updateInvestment(Long userId, Long productId, InvestmentStatus status) {
        Optional<List<Investment>> findInvestment = investmentRepository.findByUserId(userId);

        if(findInvestment.isPresent()) {
            List<Investment> investments = findInvestment.get();
            for(Investment investment : investments) {
                if(Objects.equals(investment.getProduct().getId(), productId) && investment.getStatus() == INVESTED) {
                    investment.changeStatus(status);
                    investmentRepository.save(investment);
                    return entityToResponseList(List.of(investment));
                }
            }
        }

        return emptyResponseList();
    }

    public InvestmentResponse invest(Long userId, Long productId, Long investAmount) {
        Product product = productRepository.findById(productId).orElse(getEmptyProduct());
        List<Investment> investments = investmentRepository.findByProductId(product.getId());
        InvestmentStatus investmentStatus = INVESTED;

        Long goalAmount = product.getTotalInvestingAmount();
        Long totalInvested = getCurrentTotalInvested(investments);
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

    private Long getCurrentTotalInvested(List<Investment> investments) {
        Long total = 0L;
        for (Investment investment : investments)
            total += investment.getInvestedAmount();
        return total;
    }

    private boolean isNoTotalInvestment(Long total, Long goalAmount) {
        return total >= goalAmount;
    }

    private boolean isInvestmentPossible(Long investmentAmount, Long totalInvested, Long goalAmount) {
        return investmentAmount <= (goalAmount - totalInvested);
    }

}
