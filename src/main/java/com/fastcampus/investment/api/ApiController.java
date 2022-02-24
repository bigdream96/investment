package com.fastcampus.investment.api;

import com.fastcampus.investment.constants.InvestmentStatus;
import com.fastcampus.investment.dto.response.InvestmentResponse;
import com.fastcampus.investment.dto.Message;
import com.fastcampus.investment.dto.response.ProductResponse;
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
public class ApiController {
    private static final String USER_ID = "X-USER-ID";

    private final ProductService productService;
    private final InvestmentService investmentService;

    @GetMapping("product")
    public Message<List<ProductResponse>> searchInvestmentProductList() {
        List<ProductResponse> productResponses = productService.searchInvestmentProductList();
        return Message.ok(productResponses);
    }

    @GetMapping("investment")
    public Message<List<InvestmentResponse>> searchInvestment(@RequestHeader(value = USER_ID) @NotNull @Positive Long userId) {
        List<InvestmentResponse> investmentResponses = investmentService.searchInvestment(userId);
        return Message.ok(investmentResponses);
    }

    @PostMapping("investment")
    public Message<InvestmentResponse> invest(@RequestHeader(value = USER_ID) @NotNull @Positive Long userId,
                                                              @RequestParam @NotNull @Positive Long productId,
                                                              @RequestParam @PositiveOrZero Long investAmount) {
        InvestmentResponse investmentResponse = investmentService.invest(userId, productId, investAmount);
        return Message.ok(investmentResponse);
    }

    @PutMapping("investment/{investmentId}")
    public Message<InvestmentResponse> updateInvestment(@RequestHeader(value = USER_ID) @NotNull @Positive Long userId,
                                                                        @PathVariable("investmentId") @NotNull @Positive Long investmentId,
                                                                        @RequestParam @NotNull InvestmentStatus status) {
        InvestmentResponse investmentResponse = investmentService.updateInvestment(userId, investmentId, status);
        return Message.ok(investmentResponse);
    }
}
