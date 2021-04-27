package com.datn.dto;

import com.datn.entity.Brand;
import com.datn.entity.ProductInfo;
import com.datn.entity.ProductType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto extends BaseDto{
    private String name;
    private String code;
    private Long priceSell; // giá bán
    private Double sale; // giảm giá
    private String image;
    private Long productTypeId; // loại sản phẩm
    private Long brandId;

    public ProductDto() {}
}
