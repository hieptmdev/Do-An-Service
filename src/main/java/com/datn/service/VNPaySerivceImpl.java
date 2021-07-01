package com.datn.service;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datn.config.VNPayConfig;
import com.datn.dto.VNPayResponse;
import com.datn.entity.Order;
import com.datn.repository.OrderRepository;
import com.datn.service.iservice.VNPayService;

@Service
public class VNPaySerivceImpl implements VNPayService{

    @Autowired
    OrderRepository orderRepository;
    @Override
    public String createOrderPayment(String vnp_IpAddr, String vnp_Amount, String vnp_BankCode, String vnp_OrderInfo) {
        Map<String, String> vnp_Params = new HashMap<>();
        int amount = Integer.parseInt(vnp_Amount) * 100;
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_BankCode", vnp_BankCode);
        String vnp_Command = "pay";
        vnp_Params.put("vnp_Command", vnp_Command);
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(dt);
        String vnp_CreateDate = dateString;
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        dt.setMinutes(dt.getMinutes()+15);
        String vnp_ExpireDate = formatter.format(dt);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_IpAddr",vnp_IpAddr);
        vnp_Params.put("vnp_Locale", "vn");

        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);

        String vnp_OrderType = "billpayment";
        vnp_Params.put("vnp_OrderType", vnp_OrderType);

        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_Returnurl);

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);

        String vnp_TxnRef = VNPayConfig.getRandomNumber(5);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);

        String vnp_Version = "2.0.0";
        vnp_Params.put("vnp_Version", vnp_Version);

        String vnp_hashSecret = VNPayConfig.vnp_HashSecret;


        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(fieldValue);
                //Build query
                try {
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.Sha256(VNPayConfig.vnp_HashSecret + hashData.toString());

        queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }

    @Override
    public VNPayResponse checkStatusOrderPayment(Map<String, String> all_Param) {
        String vnp_SecureHash  = all_Param.get("vnp_SecureHash");
        if(all_Param.containsKey("vnp_SecureHashType")) {
            all_Param.remove("vnp_SecureHashType");
        }
        if(all_Param.containsKey("vnp_SecureHash")) {
            all_Param.remove("vnp_SecureHash");
        }
        String signValue;
        VNPayResponse vnpRes = new VNPayResponse();
        try {
            signValue = VNPayConfig.hashAllFields(all_Param);
            if(signValue.equals(vnp_SecureHash)) {
                String vnp_ResponseCode = all_Param.get("vnp_ResponseCode");

                if(vnp_ResponseCode.equals("00")) {
                    // Chá»¯ kÃ½ há»£p lá»‡
                    String transNo = all_Param.get("vnp_TransactionNo");
                    String datePay = all_Param.get("vnp_PayDate");
                    String orderID = all_Param.get("vnp_OrderInfo");

                    Order order = orderRepository.getOne(Long.valueOf(orderID));
                    order.setStatus(5);
                    order = orderRepository.save(order);
                    vnpRes.setMessage("Payment success");

                }else {
                    vnpRes.setMessage(vnp_ResponseCode);
                }

            }else {
                // Chá»¯ kÃ½ khÃ´ng há»£p lá»‡"Invalid signature"
                vnpRes.setMessage("Invalid signature");
            }
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        return vnpRes;
    }

}
