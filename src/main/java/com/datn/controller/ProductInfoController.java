package com.datn.controller;

import com.datn.dto.ProductInfoDTO;
import com.datn.service.iservice.ProductsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/info")
@CrossOrigin("http://localhost:4200")
public class ProductInfoController {

    @Autowired
    ProductsInfoService productsInfoService;

    //lấy chi tiết theo id
    @GetMapping("/{id}")
    public ResponseEntity findInfoById(HttpServletRequest request, @PathVariable Long id){
        return ResponseEntity.ok().body(productsInfoService.findById(request, id));
    }
    @PostMapping("")
    public  ResponseEntity saveofupdatechitietProduct(HttpServletRequest request, @RequestBody ProductInfoDTO dto){
        return ResponseEntity.ok().body(productsInfoService.saveOrUpdate(request,dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(HttpServletRequest request, @PathVariable Long id){
        Map<String, String> responseData = new HashMap<>();
        String message;
        Boolean result = productsInfoService.delete(request, id);
        if (result){
            message = "Delete success!";
            responseData.put("message", message);
            return ResponseEntity.ok().body(responseData);
        }
        return ResponseEntity.notFound().build();
    }
}
