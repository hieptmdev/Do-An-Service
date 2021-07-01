package com.datn.service.iservice;

import java.util.Map;

import com.datn.dto.VNPayResponse;
public interface VNPayService {
    String createOrderPayment(String vnp_IpAddr, String vnp_Amount, String vnp_BankCode, String vnp_OrderInfo) ;

    VNPayResponse checkStatusOrderPayment(Map<String,String> all_Param);
}
