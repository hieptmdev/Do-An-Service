package com.datn.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Date;

//bảng User
@Entity
@Table
@Getter
@Setter
    public class User extends BaseEntity implements UserDetails {
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "int default 0")
    private Integer code = 0; //0-Khách 1-Nhân viên 2-Admin
    private Date dob; // ngày sinh
    private String email;
    private String phoneNumber; // số điện thoại
    private String address; // địa chỉ
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(columnDefinition = "boolean default false")
    private Boolean isAdminAccount; // loại tài khoản: ADMIN: true || CLIENT: false,
    
    public User() {}

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }
}
    
