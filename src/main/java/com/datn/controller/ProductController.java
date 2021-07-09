package com.datn.controller;

import com.datn.dto.ColorDTO;
import com.datn.dto.OderDTO;
import com.datn.dto.ProductDto;
import com.datn.dto.SizeDto;
import com.datn.entity.ImageModel;
import com.datn.entity.Product;
import com.datn.service.ProductServiceImpl;
import com.datn.service.iservice.ImageService;
import com.datn.service.iservice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:4200")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    ImageService imageService;

    @Autowired
    private ProductServiceImpl productServiceImppl;

    @GetMapping("/all")
    public ResponseEntity findAll(HttpServletRequest request) {
        return ResponseEntity.ok().body(productService.findAll());
    }

    //lọc sản phẩm theo hãng và thể loại
    @GetMapping("/getfind")
    public ResponseEntity findFillter(HttpServletRequest request, ProductDto productDto) {
        return ResponseEntity.ok().body(productService.findFillter(request, productDto));
    }

    @GetMapping("/new")
    public ResponseEntity findNew(HttpServletRequest request) {
        return ResponseEntity.ok().body(productService.findNew());
    }

    @GetMapping("/sale")
    public ResponseEntity findSale(HttpServletRequest request) {
        return ResponseEntity.ok().body(productService.findSale());
    }

    //Lấy chi tiết sản phẩm theo mã code
    @GetMapping("/codename")
    public ResponseEntity detailProduct(HttpServletRequest request, Long id){
        return ResponseEntity.ok().body(productService.searchDetailProduct(request,id));
    }

    //tìm kiếm sản phẩm
    @GetMapping("/seach")
    public ResponseEntity search(HttpServletRequest request, ProductDto dto) {
        return ResponseEntity.ok().body(productService.search(request, dto));
    }
    //all color
    @GetMapping("/allcolor")
    public ResponseEntity allColor(HttpServletRequest request) {
        return ResponseEntity.ok().body(productService.findAllColor(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity findById(HttpServletRequest request, @PathVariable Long id) {
        return ResponseEntity.ok().body(productService.findById(request, id));
    }
    //lấy sản phẩm theo id brand
    @GetMapping("/brands/{id}")
    public ResponseEntity findforBrands(HttpServletRequest request, @PathVariable Long id) {
        return ResponseEntity.ok().body(productService.findAllBrand(request, id));
    }
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity saveOrUpdate(HttpServletRequest request, ProductDto dto) {
        return ResponseEntity.ok().body(productService.saveOrUpdate(request, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(HttpServletRequest request, @PathVariable Long id) {
        Map<String, String> responseData = new HashMap<>();
        String message;
        Boolean result = productService.delete(request, id);
        if (result) {
            message = "Delete success!";
            responseData.put("message", message);
            return ResponseEntity.ok().body(responseData);
        }
        return ResponseEntity.notFound().build();
    }
}
