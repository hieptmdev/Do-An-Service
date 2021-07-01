package com.datn.controller;

import com.datn.dto.ProductDto;
import com.datn.service.iservice.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/get-by-user/{username}")
    public ResponseEntity findAll(HttpServletRequest request, @PathVariable String username){
        return ResponseEntity.ok().body(cartService.getByUser(request, username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(HttpServletRequest request, @PathVariable Long id){
        Map<String, String> responseData = new HashMap<>();
        String message;
        Boolean result = cartService.deleteCartDetail(request, id);
        if (result){
            message = "Delete success!";
            responseData.put("message", message);
            return ResponseEntity.ok().body(responseData);
        }
        return ResponseEntity.notFound().build();
    }
}
