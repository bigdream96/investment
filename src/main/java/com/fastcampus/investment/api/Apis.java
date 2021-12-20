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
public class Apis {
    private static final String USER_ID = "X-USER-ID";

    private final ProductService productService;
    private final InvestmentService investmentService;

    @GetMapping("product")
    public ResponseEntity<Message<List<ProductResponse>>> searchInvestmentProductList() {
        List<ProductResponse> productResponses = productService.searchInvestmentProductList();
        return toResponse(productResponses);
    }

    @GetMapping("investment")
    public ResponseEntity<Message<List<InvestmentResponse>>> searchInvestment(@RequestHeader(value = USER_ID) @NotNull @Positive Long userId) {
        List<InvestmentResponse> investmentResponses = investmentService.searchInvestment(userId);
        return toResponse(investmentResponses);
    }

    @PostMapping("investment")
    public ResponseEntity<Message<InvestmentResponse>> invest(@RequestHeader(value = USER_ID) @NotNull @Positive Long userId,
                                                              @RequestParam @NotNull @Positive Long productId,
                                                              @RequestParam @PositiveOrZero Long investAmount) {
        InvestmentResponse investmentResponse = investmentService.invest(userId, productId, investAmount);
        return toResponse(investmentResponse);
    }

    @PutMapping("investment/{investmentId}")
    public ResponseEntity<Message<InvestmentResponse>> updateInvestment(@RequestHeader(value = USER_ID) @NotNull @Positive Long userId,
                                                                        @PathVariable("investmentId") @NotNull @Positive Long investmentId,
                                                                        @RequestParam @NotNull InvestmentStatus status) {
        InvestmentResponse investmentResponse = investmentService.updateInvestment(userId, investmentId, status);
        return toResponse(investmentResponse);
    }

    private static <T> ResponseEntity<Message<T>> toResponse(T data) {
        return new ResponseEntity<>(Message.OK(data), OK);
    }
}
