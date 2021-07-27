package com.datn.service;

import com.datn.dto.BaseDto;
import com.datn.dto.BrandDTO;
import com.datn.entity.Brand;
import com.datn.repository.BrandRepository;
import com.datn.repository.ProductRepository;
import com.datn.service.iservice.BrandService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<BrandDTO> findAll() {
        return brandRepository.findAll()
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, BrandDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BrandDTO saveOrUpdate(HttpServletRequest request, Object object) {
        BrandDTO brandDTO=(BrandDTO) object;
        Brand brand;
        if(brandDTO != null){
            //lưu thêm mới
            if(AppUtil.NVL(brandDTO.getId())==0L){
                brand = AppUtil.mapperEntAndDto(brandDTO, Brand.class);
                brand.setCreatedDate(new Date());
                brand.setUpdatedDate(new Date());
                brand.setName(brand.getName());
                brand.setCode(brand.getCode());
            }
            //update
            else {
                brand = brandRepository.findById(brandDTO.getId()).orElse(null);

                if (brand != null){
                    Brand dataBrand = AppUtil.mapperEntAndDto(brandDTO,Brand.class); // dataBrand sau khi map đã có dủ hết data r
                    dataBrand.setId(brand.getId());
                    dataBrand.setUpdatedDate(new Date()); // chỉ cần set ngày update thôi
                    brand = dataBrand;
                }

            }
            return  AppUtil.mapperEntAndDto(brandRepository.save(brand), BrandDTO.class);

        }
        return null;

    }
    //lấy sản phẩm theo thể loại
    @Override
    public BrandDTO findById(HttpServletRequest request, Long id) {
        Brand brand = brandRepository.findById(id).orElse(null);
        if (brand !=null){
            return AppUtil.mapperEntAndDto(brand, BrandDTO.class);
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean delete(HttpServletRequest request, Long id) {
        Brand brand = brandRepository.findById(id).orElse(null);
        if(brand != null){
            productRepository.deleteAllByBrand(brand);
            brandRepository.delete(brand);
            return true;
        }

        return false;
    }

    @Override
    public List<BrandDTO> search(BrandDTO dto) {
        return brandRepository.search(dto.getName())
                .stream()
                .map(brand -> AppUtil.mapperEntAndDto(brand, BrandDTO.class))
                .collect(Collectors.toList());
    }

}
