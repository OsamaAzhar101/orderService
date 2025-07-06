package com.oasys.ProductService.service;

import com.oasys.ProductService.entity.Product;

import com.oasys.ProductService.exceptionHandler.ProductServiceCustomException;
import com.oasys.ProductService.model.ProductRequest;
import com.oasys.ProductService.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;


    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("Adding product: {}", productRequest);

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();

        productRepository.save
                (product);
        log.info("Product added successfully: {}", product);

        return product.getProductId();

    }

    @Override
    public Product getProduct(Long productId) {
        log.info("Fetching product with ID: {}", productId);
        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductServiceCustomException
                                ("Product not found with ID: " + productId,
                                        HttpStatus.NOT_FOUND.toString()));    }
}
