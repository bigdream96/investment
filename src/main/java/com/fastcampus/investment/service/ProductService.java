package com.fastcampus.investment.service;

import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.response.ProductResponse;
import com.fastcampus.investment.exception.APIException;
import com.fastcampus.investment.repository.InvestmentRepository;
import com.fastcampus.investment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fastcampus.investment.constants.ErrorCode.NO_PRODUCT_DATA;
import static com.fastcampus.investment.constants.InvestmentStatus.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final InvestmentRepository investmentRepository;

    public List<ProductResponse> searchInvestmentProductList() throws APIException {
        List<Product> products = productRepository.findCurrentDate();

        if(products.isEmpty())
            throw new APIException(NO_PRODUCT_DATA);

        List<ProductResponse> result = new ArrayList<>();
        for (Product product : products) {
            ProductResponse productResponse = ProductResponse.entityToResponse(product);
            productResponse.setInvestedAmount(totalInvestedAmount(product));
            productResponse.setInvestedCount(cntInvested(product));
            result.add(productResponse);
        }

        return result;
    }

    private Long totalInvestedAmount(Product product) {
        return investmentRepository.findByProduct(product)
                                .stream()
                                .filter(investment -> Objects.equals(investment.getStatus(), INVESTED))
                                .mapToLong(Investment::getInvestedAmount).sum();
    }

    private Integer cntInvested(Product product) {
        return investmentRepository.countByProductAndStatus(product, INVESTED).orElse(0);
    }
}
