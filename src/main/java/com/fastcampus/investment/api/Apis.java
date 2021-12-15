package com.fastcampus.investment.api;

import com.fastcampus.investment.constants.InvestmentStatus;
import com.fastcampus.investment.dto.InvestmentResponse;
import com.fastcampus.investment.dto.Message;
import com.fastcampus.investment.dto.ProductResponse;
import com.fastcampus.investment.service.InvestmentService;
import com.fastcampus.investment.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api")
@Validated
@RequiredArgsConstructor
public class Apis {
    private static final String USER_ID = "X-USER-ID";

    private final ProductService productService;
    private final InvestmentService investmentService;

    @GetMapping("product")
    public ResponseEntity<Message<List<ProductResponse>>> inquireInvestableProducts() {
        List<ProductResponse> productResponses = productService.inquireInvestableProducts();
        Message<List<ProductResponse>> message = Message.OK(productResponses);
        return new ResponseEntity<>(message, OK);
    }

    @GetMapping("investment")
    public ResponseEntity<Message<List<InvestmentResponse>>> searchInvestment(@RequestHeader(value = USER_ID) @NotNull @Positive Long userId) {
        List<InvestmentResponse> investmentResponses = investmentService.searchInvestment(userId);
        Message<List<InvestmentResponse>> message = Message.OK(investmentResponses);
        return new ResponseEntity<>(message, OK);
    }

    @PostMapping("investment")
    public ResponseEntity<Message<InvestmentResponse>> invest(@RequestHeader(value = USER_ID) @NotNull @Positive Long userId,
                                                              @RequestParam @NotNull @Positive Long productId,
                                                              @RequestParam @PositiveOrZero Long investAmount) {
        InvestmentResponse investmentResponse = investmentService.invest(userId, productId, investAmount);
        Message<InvestmentResponse> message = Message.OK(investmentResponse);
        return new ResponseEntity<>(message, OK);
    }

    @PutMapping("investment/{productId}")
    public ResponseEntity<Message<List<InvestmentResponse>>> updateInvestment(@RequestHeader(value = USER_ID) @NotNull @Positive Long userId,
                                                                              @PathVariable("productId") @NotNull @Positive Long productId,
                                                                              @RequestParam @NotBlank InvestmentStatus status) {
        List<InvestmentResponse> investmentResponses = investmentService.updateInvestment(userId, productId, status);
        Message<List<InvestmentResponse>> message = Message.OK(investmentResponses);
        return new ResponseEntity<>(message, OK);
    }
}
