package com.datn.service.iservice;

import com.datn.dto.OderDTO;
import com.datn.dto.OderDetailDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService extends BaseService{
    List<OderDTO> findByUserId(HttpServletRequest request, Long id);

    List<OderDTO> search(HttpServletRequest request, OderDTO dto);

    List<OderDetailDTO> searchOderDetail(HttpServletRequest request, OderDTO dto);
}
