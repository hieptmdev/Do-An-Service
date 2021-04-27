package com.datn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Sản phẩm
 */
@Entity
@Table
@Getter
@Setter
public class Product extends BaseEntity{
    @Column(nullable = false)
    private String name;
    private String code;
    @Column(nullable = false)
    private Long priceSell; // giá bán
    private Double sale; // giảm giá
    private String image;
    @OneToMany(mappedBy = "product")
    private List<ProductInfo> productInfoList;
    @ManyToOne
    private ProductType productType; // loại sản phẩm
    @ManyToOne
    private Brand brand; // hãng sản xuất

    public Product(){}
}
