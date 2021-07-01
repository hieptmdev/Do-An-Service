package com.datn.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VNPayRequest {
    private String vnp_Amount;
    private String vnp_BankCode;
    private String vnp_OrderInfo;
    private String delivery_mobile;
    private String delivery_address;
}
