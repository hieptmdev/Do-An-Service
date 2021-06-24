package com.datn.service;

import com.datn.dto.*;
import com.datn.entity.Brand;
import com.datn.entity.Product;
import com.datn.entity.ProductType;
import com.datn.repository.ProductTypeRepository;
import com.datn.service.iservice.ProductService;
import com.datn.service.iservice.ProductTypeService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;import java.util.stream.Collectors;


@Service
public class ProductTypeImpl implements ProductTypeService{

     @Autowired
    ProductTypeRepository productTypeRepository;

    @Override
    public List<ProductTypeDTO> findAll() {
        return productTypeRepository.getAllParent()
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, ProductTypeDTO.class))
                .collect(Collectors.toList());

    }
    @Override
    @Transactional
    public ProductTypeDTO saveOrUpdate(HttpServletRequest request, Object object) {
        ProductTypeDTO productTypeDTO =(ProductTypeDTO) object;
        ProductType type;
        if(productTypeDTO != null){
            //lưu thêm mới
            if(AppUtil.NVL(productTypeDTO.getId())==0L){
                type = AppUtil.mapperEntAndDto(productTypeDTO, ProductType.class);
                type.setCreatedDate(new Date());
                type.setUpdatedDate(new Date());
                type.setName(type.getName());
                type.setCode(type.getCode());
            }
            //update
            else {
                type = productTypeRepository.findById(productTypeDTO.getId()).orElse(null);

                if (type != null){
                    ProductType dataType = AppUtil.mapperEntAndDto(productTypeDTO,ProductType.class); // dataBrand sau khi map đã có dủ hết data r
                    dataType.setId(type.getId());
                    dataType.setUpdatedDate(new Date()); // chỉ cần set ngày update thôi
                    type = dataType;
                }

            }
            return  AppUtil.mapperEntAndDto(productTypeRepository.save(type), ProductTypeDTO.class);

        }
        return null;

    }

    @Override
    //nhận về DTO
    public ProductTypeDTO findById(HttpServletRequest request, Long id) {
        //Lưu Data vào enty
        ProductType productType = productTypeRepository.findById(id).orElse(null);
        if (productType !=null){
            //chuyển dữ liệu về DTO
            return AppUtil.mapperEntAndDto(productType, ProductTypeDTO.class);
        }

        return null;
    }


    @Override
    public Boolean delete(HttpServletRequest request, Long id) {
        ProductType productType = productTypeRepository.findById(id).orElse(null);
        if (productType != null) {
            productTypeRepository.delete(productType);
            return true;
            //xoa
        }
        return false;
    }

    //lấy sản phẩm theo thể loại
    @Override
    public List<ProductDto> findAllCategory(HttpServletRequest request, Long id) {
        return productTypeRepository.getAllByCategory(id)
                .stream()
                .map(obj -> {
                    ProductDto dto = AppUtil.mapperEntAndDto(obj, ProductDto.class);
                    dto.setColoList(obj.getProductInfoList()
                            .stream()
                            .map(productInfo -> {
                                ColorDTO colorDTO = AppUtil.mapperEntAndDto(productInfo.getColor(), ColorDTO.class);
                                colorDTO.setProductInfoId(productInfo.getId());
                                return colorDTO;
                            })
                            .collect(Collectors.toList())
                    );
                    return dto;
                }).
                collect(Collectors.toList());
    }
}




