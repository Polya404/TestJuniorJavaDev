package org.example.test.controllers;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.example.test.ProductRequest;
import org.example.test.config.JwtTokenProvider;
import org.example.test.models.Product;
import org.example.test.services.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/add")
    public ResponseEntity<String> addProducts(@RequestBody ProductRequest productRequest,
                                              @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String authorizationHeader) {
        if (StringUtils.isBlank(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        String token = authorizationHeader.substring(7);

        if (jwtTokenProvider.isInvalidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        productService.saveProducts(productRequest.getTable(), productRequest.getRecords());

        return ResponseEntity.ok("Products added successfully");
    }


    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(@RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String authorizationHeader) {
        if (StringUtils.isBlank(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
        }

        String token = authorizationHeader.substring(7);

        if (jwtTokenProvider.isInvalidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
        }

        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}

