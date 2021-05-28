package com.datn.controller;

import com.datn.dto.BrandDTO;
import com.datn.dto.OderDTO;
import com.datn.service.iservice.BrandService;
import com.datn.service.iservice.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/oders")
@CrossOrigin("http://localhost:4200")
public class OderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity findAll(){
        return ResponseEntity.ok().body(orderService.findAll());
    }

//    @GetMapping("/search")
//    public ResponseEntity search(HttpServletRequest request, BrandDTO dto){
//        return ResponseEntity.ok().body(orderService.search(dto));
//    }
    @GetMapping("/user/{id}")
    public ResponseEntity findByUserId(HttpServletRequest request, @PathVariable Long id){
        return ResponseEntity.ok().body(orderService.findByUserId(request,id));
    }
    @GetMapping("/{id}")
    public ResponseEntity findById(HttpServletRequest request, @PathVariable Long id){
        return ResponseEntity.ok().body(orderService.findById(request, id));
    }
    @PostMapping("")
    public ResponseEntity save(HttpServletRequest request, @RequestBody OderDTO dto){
        return ResponseEntity.ok().body(orderService.saveOrUpdate(request, dto));
    }

    @PutMapping("")
    public ResponseEntity update(HttpServletRequest request, @RequestBody BrandDTO dto){
        return ResponseEntity.ok().body(orderService.saveOrUpdate(request, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(HttpServletRequest request, @PathVariable Long id){
        String message;
        Boolean result = orderService.delete(request, id);
        if (result){
            message = "Delete success!";
            return ResponseEntity.ok().body(message);
        }
        return ResponseEntity.notFound().build();
    }
}
