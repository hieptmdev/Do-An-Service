package com.datn.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto extends BaseDto{
    private String name;
    private Integer code;
    private Date dob; // ngày sinh
    private String email;
    private String phoneNumber; // số điện thoại
    private String address; // địa chỉ
    private String username;
    private String password;
    private Boolean isAdminAccount; // loại tài khoản: ADMIN: true || CLIENT: false
    private String codeString;
    public UserDto() {}
}
