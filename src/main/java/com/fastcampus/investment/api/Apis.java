package com.fastcampus.investment.api;

import com.fastcampus.investment.constants.InvestmentStatus;
import com.fastcampus.investment.dto.InvestmentResponse;
import com.fastcampus.investment.dto.ProductResponse;
import com.fastcampus.investment.service.InvestmentService;
import com.fastcampus.investment.service.ProductService;
import com.fastcampus.investment.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class Apis {
    private static final String USER_ID = "X-USER-ID";

    private final ProductService productService;
    private final InvestmentService investmentService;

    @Autowired
    public Apis(ProductService productService, InvestmentService investmentService) {
        this.productService = productService;
        this.investmentService = investmentService;
    }

    @GetMapping("product")
    public ResponseEntity<?> inquireInvestableProducts() {
        List<ProductResponse> products = productService.inquireInvestableProducts();
        return new ResponseEntity<>(JsonUtil.convert(products), HttpStatus.OK);
    }

    @GetMapping("investment")
    public ResponseEntity<?> invest(@RequestHeader(value="X-USER-ID") Long userId) {
        List<InvestmentResponse> investmentResponses = investmentService.searchInvestment(userId);
        return new ResponseEntity<>(JsonUtil.convert(investmentResponses), HttpStatus.OK);
    }

    @PostMapping("investment")
    public ResponseEntity<?> searchInvestment(@RequestHeader(value="X-USER-ID") Long userId, @RequestParam Long productId, @RequestParam Long investAmount) {
        InvestmentResponse investmentResponse = investmentService.invest(userId, productId, investAmount);
        return new ResponseEntity<>(JsonUtil.convert(investmentResponse), HttpStatus.OK);
    }

    @PutMapping("investment/{productId}")
    public ResponseEntity<?> updateInvestment(@RequestHeader(value="X-USER-ID") Long userId, @PathVariable("productId") Long productId, @RequestParam InvestmentStatus status) {
        List<InvestmentResponse> investmentResponses = investmentService.updateInvestmentStatus(userId, productId, status);
        return new ResponseEntity<>(JsonUtil.convert(investmentResponses), HttpStatus.OK);
    }
}
