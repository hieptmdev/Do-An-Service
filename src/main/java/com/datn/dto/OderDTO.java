package com.datn.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

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
    private String username;
    private Long cartId;
    private List<OderDTO> oderDTOList;
    private String statusString;
    private String tenNguoiNhan;

    public  OderDTO(){}
}
