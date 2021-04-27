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
    private Product product;
    private String color; // màu sắc
    private Integer size; // kích cỡ
    private String image; // ảnh
    private Long numberProduct; // số sản phẩm

    public ProductInfo() {}
}
