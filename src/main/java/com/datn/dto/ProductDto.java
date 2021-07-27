package com.datn.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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
    private String productTypeName;
    private Long brandId;
    private String brandName;
    private Long startPrice;
    private Long endPrice;
    private String status;
    private String mieuTa;
    private List<SizeDto> sizeList;
    private List<ColorDTO> coloList;
    private Long cartId;
    private MultipartFile fileImg;
    private Long productInfoId;
    private Long quantity;//số lượng chọn
    private List<ProductInfoDTO> productInfoList;

    public ProductDto() {}
}
