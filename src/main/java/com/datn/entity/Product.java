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
    private String status;
    private String mieuTa;

    @OneToMany(mappedBy = "product")
    private List<ProductInfo> productInfoList;

    @ManyToOne
    private ProductType productType;
    // loại sản phẩm
    // ở đây là 1 object, khi truy vấn = jpa sẽ nhận đc toàn bộ giá trị theo khóa này
    //nhiều sản phẩm cùng 1 loại
    @ManyToOne
    private Brand brand;
    //nhieu sp thuoc 1 brand

    public Product(){}
}
