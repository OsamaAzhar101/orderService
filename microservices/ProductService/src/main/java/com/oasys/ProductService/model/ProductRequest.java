package com.oasys.ProductService.model;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private String quantity;
}
