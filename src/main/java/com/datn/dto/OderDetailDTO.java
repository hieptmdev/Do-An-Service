package com.datn.dto;

import com.datn.entity.ProductInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OderDetailDTO  extends  BaseDto{
    private Long orderId;
    private Long numberProduct;
    private String productInfoProductName;
    private String productInfoColorName;
    private String productInfoProductProductTypeName;
    private Long productInfoProductPriceSell;
}
