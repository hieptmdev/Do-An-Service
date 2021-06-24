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

    //Lấy chi tiết sản phẩm
    @GetMapping("/codename")
    public ResponseEntity detailProduct(HttpServletRequest request, Long id){
        return ResponseEntity.ok().body(productService.searchDetailProduct(request,id));
    }

    //tìm kiếm sản phẩm
    @GetMapping("/seach")
    public ResponseEntity search(HttpServletRequest request, ProductDto dto) {
        return ResponseEntity.ok().body(productService.search(request, dto));
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


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveOrUpdate(HttpServletRequest request, @RequestBody ProductDto dto) {
        return ResponseEntity.ok().body(productService.saveOrUpdate(request, dto));
    }
//
//    @PostMapping("/save")
//    public String save(@RequestParam String name,
//                               @RequestParam String code,
//                               @RequestParam Long priceSell, // giá bán
//                               @RequestParam Double sale, // giảm giá
//                               @RequestParam("imageFile") MultipartFile file,String image,
//                               @RequestParam Long productTypeId, // loại sản phẩm
//                               @RequestParam String productTypeName,// ở đây đặt tên giống vs tên ở bên ent + tên biến giá trị của đối tượng này: productType + Name, thì modelmapper nó phân truy cập sâu vào bên trong đối tượng lấy giá trị tướng ứng
//                               @RequestParam Long brandId,
//                               @RequestParam String brandName,
//                               @RequestParam Long startPrice,
//                               @RequestParam String status,
//                               @RequestParam String mieuTa,
//                               @RequestParam List<ColorDTO> coloList,
//                               @RequestParam Long productInfoId
//    ) {
//        try {
//            File newFile = new File("F:\\DoAn_SpringBoots\\do-an-web\\src\\assets\\style\\img\\"+file.getOriginalFilename());
//            FileOutputStream fileOutputStream;
//            fileOutputStream=new FileOutputStream(newFile);
//            fileOutputStream.write(file.getBytes());
//            fileOutputStream.close();
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        Product products = new Product();
//        imageService.saveProduct(products
//                .setImage("assets/style/img/"+file.getOriginalFilename());
//        );
//        return "Success";
//    }

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
