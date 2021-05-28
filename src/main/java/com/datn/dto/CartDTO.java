package com.datn.dto;

import com.datn.entity.CartDetaill;
import com.datn.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDTO extends BaseDto{
    private Long userId;
    private Long totalMonneyCart; //tong gia tri all don hang
    private Long totalNumber;
    private List<CartDetaillDto> cartDetaills;

    public CartDTO(){}
}
