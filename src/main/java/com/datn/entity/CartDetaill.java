package com.datn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
//chi tiết giỏ hàng
public class CartDetaill extends BaseEntity{
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private ProductInfo productInfo;
    private Long numberPro;// Số lượng sản phẩm cần mua của 1 sanr phẩm
    private Long totalMoney; //tổng tiền của 1 sản phẩm VD : 2*400 = 800

}
