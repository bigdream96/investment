package com.fastcampus.investment.service;

import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.response.ProductResponse;
import com.fastcampus.investment.repository.InvestmentRepository;
import com.fastcampus.investment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.fastcampus.investment.constants.InvestmentStatus.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final InvestmentRepository investmentRepository;

    public List<ProductResponse> searchInvestmentProductList() {
        List<Product> products = productRepository.findCurrentDate();

        List<ProductResponse> result = new ArrayList<>();
        for (Product product : products) {
            ProductResponse productResponse = ProductResponse.of(product);
            productResponse.setTotalStatistics(sumInvestedAmount(product), cntInvested(product));
            result.add(productResponse);
        }

        return result;
    }

    private Long sumInvestedAmount(Product product) {
        return investmentRepository.sumInvestedAmount(product);
    }

    private Integer cntInvested(Product product) {
        return investmentRepository.countByProductAndStatus(product, INVESTED);
    }
}
