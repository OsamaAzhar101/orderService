package com.oasys.ProductService.service;

import com.oasys.ProductService.entity.Product;
import com.oasys.ProductService.model.ProductRequest;

public interface ProductService {

    Long addProduct(ProductRequest productRequest);

    Product getProduct(Long productId);
}
