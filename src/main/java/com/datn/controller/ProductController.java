package com.datn.controller;

import com.datn.dto.ProductDto;
import com.datn.service.iservice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:4200")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity findAll(HttpServletRequest request){
        return ResponseEntity.ok().body(productService.findAll());
    }

    @GetMapping("/getfind")
        public ResponseEntity findFillter(HttpServletRequest request, ProductDto productDto ) {
        return ResponseEntity.ok().body(productService.findFillter(request,productDto));
    }

    @GetMapping("/new")
    public ResponseEntity findNew(HttpServletRequest request){
        return ResponseEntity.ok().body(productService.findNew());
    }

    @GetMapping("/sale")
    public ResponseEntity findSale(HttpServletRequest request){
        return ResponseEntity.ok().body(productService.findSale());
    }
    @GetMapping("/seach")
    public ResponseEntity search(HttpServletRequest request, ProductDto dto){
        return ResponseEntity.ok().body(productService.search(request,dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(HttpServletRequest request, @PathVariable Long id){
        return ResponseEntity.ok().body(productService.findById(request, id));
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity findforBrands(HttpServletRequest request, @PathVariable Long id){
        return ResponseEntity.ok().body(productService.findAllBrand(request, id));
    }


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveOrUpdate(HttpServletRequest request, @RequestBody ProductDto dto){
        return ResponseEntity.ok().body(productService.saveOrUpdate(request, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(HttpServletRequest request, @PathVariable Long id){
        Map<String, String> responseData = new HashMap<>();
        String message;
        Boolean result = productService.delete(request, id);
        if (result){
            message = "Delete success!";
            responseData.put("message",message);
            return ResponseEntity.ok().body(responseData);
        }
        return ResponseEntity.notFound().build();
    }
}
