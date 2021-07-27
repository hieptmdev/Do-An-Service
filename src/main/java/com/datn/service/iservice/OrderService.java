package com.datn.service.iservice;

import com.datn.dto.OderDTO;
import com.datn.dto.OderDetailDTO;
import com.datn.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface OrderService extends BaseService{
    List<OderDTO> findByUserId(HttpServletRequest request, Long id);

    List<OderDTO> search(HttpServletRequest request, OderDTO dto);

    List<OderDetailDTO> searchOderDetail(HttpServletRequest request, OderDTO dto);

    Map<String, Double> getChartDateByYear(Long year);

    List<OderDTO> selectOderByStatus (Integer status);
}
