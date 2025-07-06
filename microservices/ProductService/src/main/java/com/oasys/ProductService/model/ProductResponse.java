package com.oasys.ProductService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

    private Long productId;
    private String name;
    private String description;
    private Double price;
    private String quantity;
}
