package com.datn.controller;

import com.datn.dto.OderDTO;
import com.datn.dto.UserDto;
import com.datn.entity.User;
import com.datn.repository.OrderRepositoryCustom;
import com.datn.service.iservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:4200")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/all")
    public ResponseEntity findAll(){
        return ResponseEntity.ok().body(userService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity findById(HttpServletRequest request, @PathVariable Long id){
        return ResponseEntity.ok().body(userService.findById(request, id));
    }

    //Đăng ký
    @PostMapping("")
    public ResponseEntity saveOrUpdate(HttpServletRequest request, @RequestBody UserDto dto){
        return ResponseEntity.ok().body(userService.saveOrUpdate(request, dto));
    }
    @PostMapping("/seach")
    public ResponseEntity search(HttpServletRequest request,@RequestBody UserDto dto){
        return ResponseEntity.ok().body(userService.search(request,dto));
    }
    @GetMapping("select-user-by-code/{code}")
    public ResponseEntity selectUserByCode (@PathVariable Integer code){
        return ResponseEntity.ok().body(userService.selectUserByCode(code));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(HttpServletRequest request, @PathVariable Long id){
        Map<String, String> responseData = new HashMap<>();
        String message;
        Boolean result = userService.delete(request, id);
        if (result){
            message = "Delete success!";
            responseData.put("message", message);
            return ResponseEntity.ok().body(responseData);
        }
        return ResponseEntity.notFound().build();
    }
}
