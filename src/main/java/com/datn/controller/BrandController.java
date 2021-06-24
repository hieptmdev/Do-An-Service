package com.datn.controller;

import com.datn.dto.BrandDTO;
import com.datn.dto.ProductDto;
import com.datn.entity.Brand;
import com.datn.service.iservice.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/brands")
@CrossOrigin("http://localhost:4200")
public class BrandController {
    @Autowired
    BrandService brandService;

    @GetMapping("/all")
    public ResponseEntity findAll(){
        return ResponseEntity.ok().body(brandService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity search(HttpServletRequest request, BrandDTO dto){
        return ResponseEntity.ok().body(brandService.search(dto));
   }

    @GetMapping("/{id}")
    public ResponseEntity findById(HttpServletRequest request, @PathVariable Long id){
        return ResponseEntity.ok().body(brandService.findById(request, id));
    }
    @PostMapping("")
    public ResponseEntity saveOrUpdate(HttpServletRequest request, @RequestBody BrandDTO dto){
        return ResponseEntity.ok().body(brandService.saveOrUpdate(request, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(HttpServletRequest request, @PathVariable Long id){
        Map<String, String> responseData = new HashMap<>();
        String message;
        Boolean result = brandService.delete(request, id);
        if (result){
            message = "Delete success!";
            responseData.put("message", message);
            return ResponseEntity.ok().body(responseData);
        }
        return ResponseEntity.notFound().build();
    }


}
