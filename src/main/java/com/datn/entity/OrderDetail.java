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
public class OrderDetail extends BaseEntity{
    @ManyToOne
    private Order order;
    @ManyToOne
    private ProductInfo productInfo;
    private Long numberProduct;
}
