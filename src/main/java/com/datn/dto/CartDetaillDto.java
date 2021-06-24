package com.datn.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CartDetaillDto extends BaseDto{
    private Long cartId;
    private Long productId;
    private String productName;
    private String productInfoProductImage;
    private Long productInfoProductPriceSell;
    private Long numberPro;// Số lượng sản phẩm cần mua của 1 sanr phẩm
    private Long totalMoney; //tổng tiền của 1 sản phẩm VD : 2*400 = 800
    private String productInfoColorName;
}
