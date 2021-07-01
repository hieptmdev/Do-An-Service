package com.datn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInfoDTO extends BaseDto{
    // đậy là đối tượng bn nhận đc từ font nó chỉ gồm có id
    private Long productId; // id cảu product đây, id kia là của product info, bn có update info ko,ừ nhỉ có
    private String productName;
    private Long numberProduct;
    private Long colorId;
    private String colorName;

    public ProductInfoDTO() {
    }
}
