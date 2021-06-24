package com.datn.service.iservice;


import com.datn.dto.ProductDto;
import com.datn.dto.ProductTypeDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductTypeService extends BaseService{
    List<ProductDto> findAllCategory(HttpServletRequest request, Long id);
}
