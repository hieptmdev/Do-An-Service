package com.datn.controller;

import com.datn.service.iservice.OderdetaillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
