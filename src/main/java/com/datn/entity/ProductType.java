package com.datn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Loại sản phẩm
 */
@Entity
@Table
@Getter
@Setter
public class ProductType extends BaseEntity{
    @Column(nullable = false)
    private String name;
    private String code;
    @ManyToOne
    private ProductType productTypeParent;

    public ProductType(){}
}
