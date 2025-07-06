package com.oasys.ProductService.controller;

import com.oasys.ProductService.entity.Product;
import com.oasys.ProductService.model.ProductRequest;
import com.oasys.ProductService.model.ProductResponse;
import com.oasys.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {

      Long productId = productService.addProduct(productRequest);

        return new ResponseEntity<>(productId, org.springframework.http.HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
        Product product = productService.getProduct(productId);

        ProductResponse productResponse = ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();

        return ResponseEntity.ok(productResponse);
    }

}
