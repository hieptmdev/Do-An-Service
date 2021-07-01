package com.datn.service.iservice;

import com.datn.dto.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

public interface CartService extends BaseService{

    CartDTO getByUser(HttpServletRequest request, String username);
    Boolean deleteCartDetail(HttpServletRequest request, Long id);
}
