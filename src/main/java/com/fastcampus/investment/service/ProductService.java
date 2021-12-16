package com.fastcampus.investment.service;

import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.response.ProductResponse;
import com.fastcampus.investment.exception.APIException;
import com.fastcampus.investment.repository.InvestmentRepository;
import com.fastcampus.investment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.fastcampus.investment.constants.ErrorCode.NO_PRODUCT_DATA;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final InvestmentRepository investmentRepository;

    public List<ProductResponse> inquireInvestableProducts() throws APIException {
        List<ProductResponse> result = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        LocalDate curDate = LocalDate.now();

        if(products.isEmpty())
            throw new APIException(NO_PRODUCT_DATA);

        for (Product product : products) {
            if (product.getStartedAt().isBefore(curDate)
                    && product.getFinishedAt().isAfter(curDate)) {
                ProductResponse productResponse = ProductResponse.entityToResponse(product);
                productResponse.setInvestedAmount(investmentRepository.findByProduct(product).stream().mapToLong(Investment::getInvestedAmount).sum());
                productResponse.setInvestedCount(investmentRepository.countByProduct(product).orElse(0));
                result.add(productResponse);
            }
        }

        return result;
    }
}
