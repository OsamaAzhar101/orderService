package com.oasys.OrderService.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.oasys.OrderService.external.client.model.ProductRequest;
import com.oasys.OrderService.external.client.model.ProductResponse;

@FeignClient(name = "PRODUCT-SERVICE/product")
public interface ProductService {


    @PostMapping
     ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest);

    @GetMapping("/{id}")
     ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId);

    @PutMapping("/reduceQuantity/{id}")
     ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") long productId,
            @RequestParam long quantity
    );

}
