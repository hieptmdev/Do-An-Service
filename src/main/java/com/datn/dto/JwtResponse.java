package com.datn.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private Date expiryDate;
    private String type = "Bearer";
    private String username;
    public JwtResponse(String token, String username, Date expiryDate){
        this.token = token;
        this.username = username;
        this.expiryDate = expiryDate;
    }
}
