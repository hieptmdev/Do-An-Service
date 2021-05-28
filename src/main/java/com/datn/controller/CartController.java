package com.datn.controller;

import com.datn.dto.ProductDto;
import com.datn.service.iservice.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/carts")
@CrossOrigin("http://localhost:4200")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/all")
    public ResponseEntity findAll(){
        return ResponseEntity.ok().body(cartService.findAll());
    }

    @PostMapping("")
    public ResponseEntity saveOrUpdate(HttpServletRequest request, @RequestBody ProductDto dto){
        return ResponseEntity.ok().body(cartService.saveOrUpdate(request, dto));
    }
}
