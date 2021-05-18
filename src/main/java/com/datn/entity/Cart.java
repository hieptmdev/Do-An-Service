package com.datn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Cart extends BaseEntity{

    @ManyToOne
    private User user;
    private Long totalMonneyCart; //tong gia tri all don hang
    private Long totalNumber; //tong so luong san pham

    @ManyToOne
    private Product product ;

    public Cart() {
    }


}
