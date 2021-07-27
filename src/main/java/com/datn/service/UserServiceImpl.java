package com.datn.service;

import com.datn.dto.OderDTO;
import com.datn.dto.UserDto;
import com.datn.entity.User;
import com.datn.repository.UserRepository;
import com.datn.service.iservice.UserService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(obj ->
                {
                    UserDto dto = AppUtil.mapperEntAndDto(obj, UserDto.class);
                    switch (obj.getCode()) {
                        case 0:
                            dto.setCodeString("Khách Hàng");
                            break;
                        case 1:
                            dto.setCodeString("Nhân Viên");
                            break;
                        case 2:
                            dto.setCodeString("Quản Lý");
                            break;
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDto saveOrUpdate(HttpServletRequest request, Object object) {
        UserDto userDto = (UserDto) object;
        User user;
        if (userDto != null) {
            //lưu thêm mới
            if (AppUtil.NVL(userDto.getId()) == 0L) {
                user = AppUtil.mapperEntAndDto(userDto, User.class);
                user.setCreatedDate(new Date());
                user.setUpdatedDate(new Date());
                user.setIsAdminAccount(false);
                user.setCode(0);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            //update
            else {
                user = userRepository.findById(userDto.getId()).orElse(null);

                if (user != null) {
                    User dataUser = AppUtil.mapperEntAndDto(userDto, User.class);
                    dataUser.setId(user.getId());
                    dataUser.setUpdatedDate(new Date());
                    user = dataUser;
                }

            }
            return AppUtil.mapperEntAndDto(userRepository.save(user), UserDto.class);

        }
        return null;
    }

    @Override
    public UserDto findById(HttpServletRequest request, Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return AppUtil.mapperEntAndDto(user, UserDto.class);
        }
        return null;
    }

    @Override
    public Boolean delete(HttpServletRequest request, Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }

        return false;
    }

    @Override
    public List<UserDto> selectUserByCode(Integer code) {
        List<User> users = userRepository.findByCode(code);
        List<UserDto> userDtos = users.stream().map(u -> {
            UserDto dto = AppUtil.mapperEntAndDto(u, UserDto.class);
            switch (u.getCode()) {
                case 0:
                    dto.setCodeString("Khách Hàng");
                    break;
                case 1:
                    dto.setCodeString("Nhân Viên");
                    break;
                case 2:
                    dto.setCodeString("Quản Lý");
                    break;
            }
            return dto;
        }).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public List<UserDto> search(HttpServletRequest request, UserDto dto1) {
        return userRepository.search(dto1.getName().toLowerCase().trim())
                .stream()
                .map(obj ->
                {
                    UserDto dto = AppUtil.mapperEntAndDto(obj, UserDto.class);
                    switch (obj.getCode()) {
                        case 0:
                            dto.setCodeString("Khách Hàng");
                            break;
                        case 1:
                            dto.setCodeString("Nhân Viên");
                            break;
                        case 2:
                            dto.setCodeString("Quản Lý");
                            break;
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
