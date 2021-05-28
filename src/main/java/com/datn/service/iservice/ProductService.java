package com.datn.service.iservice;

import com.datn.dto.ProductDto;
import com.datn.dto.ProductTypeDTO;
import com.datn.entity.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService extends BaseService{
    List<ProductDto> search(HttpServletRequest request, ProductDto dto);
    List<ProductDto> findNew();
    List<ProductDto>findSale();
    List<ProductDto> findAllBrand(HttpServletRequest request, Long id);
    List<ProductDto> findFillter(HttpServletRequest request, ProductDto dto);
}
