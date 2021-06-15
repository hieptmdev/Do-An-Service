package com.datn.controller;

import com.datn.dto.BrandDTO;
import com.datn.dto.ProductTypeDTO;
import com.datn.entity.ProductType;
import com.datn.service.iservice.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
    @RequestMapping("/type")
@CrossOrigin("http://localhost:4200")
public class ProductTypeController {
    @Autowired
    ProductTypeService productTypeService;

    @GetMapping("/all")
    public ResponseEntity findAll(){
        return ResponseEntity.ok().body(productTypeService.findAll());
    }

    //laasy san pham theo id category
    @GetMapping("/{id}")
    public ResponseEntity categoryID(HttpServletRequest request, @PathVariable Long id){
        return  ResponseEntity.ok().body(productTypeService.findAllCategory(request, id));
    }
    @GetMapping("/cate/{id}")
    public ResponseEntity findById(HttpServletRequest request, @PathVariable Long id){
        return ResponseEntity.ok().body(productTypeService.findById(request, id));
    }
    @PostMapping("")
    public ResponseEntity saveOrUpdate(HttpServletRequest request, @RequestBody ProductTypeDTO dto){
        return ResponseEntity.ok().body(productTypeService.saveOrUpdate(request, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(HttpServletRequest request, @PathVariable Long id){
        Map<String, String> responseData = new HashMap<>();
        String message;
        Boolean result = productTypeService.delete(request, id);
        if (result){
            message = "Delete success!";
            responseData.put("message", message);
            return ResponseEntity.ok().body(responseData);
        }
        return ResponseEntity.notFound().build();
    }
}
