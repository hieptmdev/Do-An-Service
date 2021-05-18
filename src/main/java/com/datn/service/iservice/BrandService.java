package com.datn.service.iservice;

import com.datn.dto.BrandDTO;
import com.datn.entity.Brand;

import java.util.List;

public interface BrandService extends BaseService{
    List<BrandDTO> search(BrandDTO dto);
}
