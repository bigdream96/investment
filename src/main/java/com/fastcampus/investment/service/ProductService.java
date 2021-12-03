package com.fastcampus.investment.service;

import com.fastcampus.investment.domain.Investment;
import com.fastcampus.investment.domain.Product;
import com.fastcampus.investment.dto.ProductResponse;
import com.fastcampus.investment.repository.InvestmentRepository;
import com.fastcampus.investment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private static final Product EMPTY_PRODUCT = new Product();

    private final ProductRepository productRepository;
    private final InvestmentRepository investmentRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, InvestmentRepository investmentRepository) {
        this.productRepository = productRepository;
        this.investmentRepository = investmentRepository;
    }

    public static Product getEmptyProduct() {
        return EMPTY_PRODUCT;
    }

    public List<ProductResponse> inquireInvestableProducts() {
        List<ProductResponse> result = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        LocalDate curDate = LocalDate.now();

        for (Product product : products) {
            if (product.getStartedAt().isBefore(curDate)
                    && product.getFinishedAt().isAfter(curDate)) {
                ProductResponse prDto = ProductResponse.toDto(product);
                prDto.setInvestedAmount(calcInvestedAmount(product));
                prDto.setInvestedCount(cntInvested(product));
                result.add(prDto);
            }
        }

        return result;
    }

    private long calcInvestedAmount(Product product) {
        List<Investment> investments = investmentRepository.findByProduct(product);

        long total = 0L;
        for(Investment investment : investments)
            total += investment.getInvestedAmount();

        return total;
    }

    private int cntInvested(Product product) {
        List<Investment> investments = investmentRepository.findByProduct(product);
        return investments.size();
    }
}
