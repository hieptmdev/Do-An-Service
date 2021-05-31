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
    private String productTypeName; // ở đây đặt tên giống vs tên ở bên ent + tên biến giá trị của đối tượng này: productType + Name, thì modelmapper nó phân truy cập sâu vào bên trong đối tượng lấy giá trị tướng ứng
    private Long brandId;
    private String brandName;
    private Long startPrice;
    private Long endPrice;
    private String status;
    private String mieuTa;
    private List<SizeDto> sizeList;
    private List<ColorDTO> coloList;
    private Long cartId;

    public ProductDto() {}
}
