package com.datn.service.iservice;

import com.datn.dto.OderDTO;
import com.datn.dto.UserDto;
import com.datn.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService extends BaseService, UserDetailsService {
    List<UserDto> selectUserByCode (Integer code);
    List<UserDto> search(HttpServletRequest request, UserDto dto);
}
