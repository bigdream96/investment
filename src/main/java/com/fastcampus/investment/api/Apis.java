package com.fastcampus.investment.api;

import com.fastcampus.investment.dto.ProductResponse;
import com.fastcampus.investment.service.ProductService;
import com.fastcampus.investment.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class Apis {
    private static final String USER_ID = "X-USER-ID";

    private final ProductService productService;

    @Autowired
    public Apis(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("product")
    public ResponseEntity<?> inquireInvestableProducts() {
        List<ProductResponse> products = productService.inquireInvestableProducts();
        return new ResponseEntity<>(JsonUtil.convert(products), HttpStatus.OK);
    }
}
