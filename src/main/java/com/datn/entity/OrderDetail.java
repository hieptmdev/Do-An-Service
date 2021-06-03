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
    //nhiều chi tiết có trong 1 oder
    private Order order;
    @ManyToOne
    //nhiều chi tiết thuộc sản phẩm
    private ProductInfo productInfo;
    //truy ván sẽ dc theo khóa này
    private Long numberProduct;
}
