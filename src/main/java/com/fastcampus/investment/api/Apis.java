package com.fastcampus.investment.api;

import com.fastcampus.investment.constants.InvestmentStatus;
import com.fastcampus.investment.dto.InvestmentResponse;
import com.fastcampus.investment.dto.ProductResponse;
import com.fastcampus.investment.service.InvestmentService;
import com.fastcampus.investment.service.ProductService;
import com.fastcampus.investment.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class Apis {
    private static final String USER_ID = "X-USER-ID";

    private final ProductService productService;
    private final InvestmentService investmentService;

    @GetMapping("product")
    public ResponseEntity<Map<String, List<ProductResponse>>> inquireInvestableProducts() {
        List<ProductResponse> products = productService.inquireInvestableProducts();
        return new ResponseEntity<>(JsonUtil.convert(products), HttpStatus.OK);
    }

    @GetMapping("investment")
    public ResponseEntity<Map<String, List<InvestmentResponse>>> searchInvestment(@RequestHeader(value = USER_ID) Long userId) {
        List<InvestmentResponse> investmentResponses = investmentService.searchInvestment(userId);
        return new ResponseEntity<>(JsonUtil.convert(investmentResponses), HttpStatus.OK);
    }

    @PostMapping("investment")
    public ResponseEntity<Map<String, InvestmentResponse>> invest(@RequestHeader(value = USER_ID) Long userId,
                                                                  @RequestParam Long productId,
                                                                  @RequestParam Long investAmount) {
        InvestmentResponse investmentResponse = investmentService.invest(userId, productId, investAmount);
        return new ResponseEntity<>(JsonUtil.convert(investmentResponse), HttpStatus.OK);
    }

    @PutMapping("investment/{productId}")
    public ResponseEntity<Map<String, List<InvestmentResponse>>> updateInvestment(@RequestHeader(value = USER_ID) Long userId,
                                                                                  @PathVariable("productId") Long productId,
                                                                                  @RequestParam InvestmentStatus status) {
        List<InvestmentResponse> investmentResponses = investmentService.updateInvestment(userId, productId, status);
        return new ResponseEntity<>(JsonUtil.convert(investmentResponses), HttpStatus.OK);
    }
}
