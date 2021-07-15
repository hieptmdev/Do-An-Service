package com.datn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Thông tin chi tiết sản phẩm
 */
@Entity
@Table
@Getter
@Setter
public class ProductInfo extends BaseEntity{
    @ManyToOne
    private Product product; // từ productId tìm đc product
    @ManyToOne
    private Color color; // màu sắc // từ colorId tìm đc color
    private String image; // ảnh
    private Long numberProduct; // số sản phẩm - so luong kho // gán numberProduct vào đây
    public ProductInfo() {}
}
