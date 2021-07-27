package com.datn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


//Hóa đơn
@Entity
@Table(name = "_order")
@Getter
@Setter
public class Order extends BaseEntity{
    @Column(nullable = false)
    private String code; // mã đơn hàng
    private Long numberProduct; // số sản phẩm
    private Long totalResult; // tổng giá trị đơn hàng
    private Date deliveryDate; // ngày giao hàng
    private String deliveryAddress; // địa chỉ giao hàng
    private String phoneNumber; // số điện thoại người nhận hàng
    private String usernameKH; //tên người mua nhập
    @Column(columnDefinition = "int default 0")
    private Integer status = 0; // trạng thái: 0 - Chuẩn bị hàng; 1 - Đang giao; 2 - Đã giao
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
    public Order() {
    }
}
