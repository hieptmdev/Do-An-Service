package com.datn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Cart extends BaseEntity{

    @OneToOne
    private User user;
    private Long totalMonneyCart; //tong gia tri all don hang
    private Long totalNumber; //tong so luong san pham

    @OneToMany(mappedBy = "cart")
    //1 cart có nhiều sp
    private List<CartDetaill> cartDetaills;

    public Cart() {
    }


}
