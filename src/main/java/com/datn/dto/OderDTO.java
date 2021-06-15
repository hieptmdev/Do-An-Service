package com.datn.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Getter
@Setter
public class OderDTO extends  BaseDto{
    private String code; // mã đơn hàng
    private Long numberProduct; // số sản phẩm
    private Long totalResult; // tổng giá trị đơn hàng
    private Date deliveryDate; // ngày giao hàng
    private String deliveryAddress; // địa chỉ giao hàng
    private String phoneNumber; // số điện thoại người nhận hàng
    private Integer status; // trạng thái: 0 - Chuẩn bị hàng; 1 - Đang giao; 2 - Đã giao
    private Long userId;
    private Long cartId;

    public  OderDTO(){}

}
