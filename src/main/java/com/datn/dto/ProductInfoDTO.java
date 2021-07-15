package com.datn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInfoDTO extends BaseDto{
    private Long productId;
    private String productName;
    private Long numberProduct;
    private Long colorId;
    private String colorName;

    public ProductInfoDTO() {
    }
}
