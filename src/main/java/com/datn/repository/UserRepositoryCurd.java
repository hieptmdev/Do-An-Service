package com.datn.repository;

import com.datn.dto.UserDto;
import com.datn.entity.User;

import java.util.List;

public interface UserRepositoryCurd {
    List<User> findByCode (Integer code);
}
