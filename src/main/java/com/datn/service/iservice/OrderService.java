package com.datn.service.iservice;

import com.datn.dto.OderDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService extends BaseService{
    List<OderDTO> findByUserId(HttpServletRequest request, Long id);
}
