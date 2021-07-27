package com.datn.controller;

import com.datn.config.JwtConfig;
import com.datn.dto.JwtResponse;
import com.datn.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtConfig jwtConfig;

    @PostMapping("/token")
    public ResponseEntity<?> getToken(@RequestBody UserDto dto){
        Date expiryDate;
        String jwtToken;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();


        jwtToken = jwtConfig.generateToken(authentication);
        expiryDate = jwtConfig.getExpiryDate(jwtToken);

        return new ResponseEntity<>(new JwtResponse(jwtToken, userDetails.getUsername(), expiryDate), HttpStatus.OK);
    }

}
