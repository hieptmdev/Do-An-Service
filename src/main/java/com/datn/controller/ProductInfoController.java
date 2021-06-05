package com.datn.controller;

import com.datn.dto.ProductInfoDTO;
import com.datn.service.iservice.ProductsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/info")
@CrossOrigin("http://localhost:4200")
public class ProductInfoController {

    @Autowired
    ProductsInfoService productsInfoService;

    @GetMapping("/{id}")
    public ResponseEntity findInfoById(HttpServletRequest request, @PathVariable Long id){
        return ResponseEntity.ok().body(productsInfoService.findById(request, id));
    }
    @PostMapping("/{id}")
    public  ResponseEntity saveofupdatechitietProduct(HttpServletRequest request, @RequestBody ProductInfoDTO dto){
        return ResponseEntity.ok().body(productsInfoService.saveOrUpdate(request,dto));
    }
}
