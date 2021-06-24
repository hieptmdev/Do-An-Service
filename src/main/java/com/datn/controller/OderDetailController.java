package com.datn.controller;

import com.datn.dto.BrandDTO;
import com.datn.dto.OderDTO;
import com.datn.service.iservice.OderdetaillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/detail")
@CrossOrigin("http://localhost:4200")
public class OderDetailController {
    @Autowired
    OderdetaillService oderdetaillService;

    @GetMapping("")
    public ResponseEntity findAll(HttpServletRequest request){
        return ResponseEntity.ok().body(oderdetaillService.findAll());
    }
    // cập nhập
    @PutMapping("")
    public ResponseEntity update(HttpServletRequest request, @RequestBody BrandDTO dto){
        return ResponseEntity.ok().body(oderdetaillService.saveOrUpdate(request, dto));
    }
    //thêm
    @PostMapping("")
    public ResponseEntity save(HttpServletRequest request, @RequestBody OderDTO dto){
        return ResponseEntity.ok().body(oderdetaillService.saveOrUpdate(request, dto));
    }


}
