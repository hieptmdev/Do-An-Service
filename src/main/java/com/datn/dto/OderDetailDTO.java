package com.datn.dto;

import com.datn.entity.ProductInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OderDetailDTO  extends  BaseDto{
    private Long numberProduct;
    // private ProductInfo productInfo;
    private String productName;

}
