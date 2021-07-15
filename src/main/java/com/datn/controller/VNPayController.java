package com.datn.controller;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datn.config.VNPayConfig;
import com.datn.dto.VNPayRequest;
import com.datn.dto.VNPayResponse;
import com.datn.service.iservice.VNPayService;

@RestController
@RequestMapping("/vnpay")
@CrossOrigin("http://localhost:4200")
public class VNPayController {
    @Autowired
    VNPayService vnpayService;

    @PostMapping(value = "/payment")
    public ResponseEntity<?> getUrl(HttpServletRequest res, @RequestBody VNPayRequest dto)  {
        String vnp_IpAddr = VNPayConfig.getIpAddress(res);
        String message = vnpayService.createOrderPayment(vnp_IpAddr, dto.getVnp_Amount(), dto.getVnp_BankCode(),dto.getVnp_OrderInfo());
        return new ResponseEntity<>(new VNPayResponse(message), HttpStatus.OK);
    }
    @GetMapping(value = "/vnpay_ipn")
    public ResponseEntity<?> getIPN(@RequestParam Map<String, String> all_Param ) {
        VNPayResponse vnp= vnpayService.checkStatusOrderPayment(all_Param);
        return new ResponseEntity<>(vnp, HttpStatus.OK);
    }
}
