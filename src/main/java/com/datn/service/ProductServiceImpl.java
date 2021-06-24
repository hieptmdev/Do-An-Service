package com.datn.service;

import com.datn.dto.*;
import com.datn.entity.*;
import com.datn.repository.BrandRepository;
import com.datn.repository.ProductRepository;
import com.datn.repository.ProductTypeRepository;
import com.datn.service.iservice.ProductService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> productDtoList;
        List<Product> productList = productRepository.findAll();
        if (!AppUtil.isNullOrEmpty(productList)){
            productDtoList = productList.stream()
                    //chuyển từ ent => dto
                    .map(ent -> {
                        ProductDto dto = AppUtil.mapperEntAndDto(ent, ProductDto.class);
                        dto.setColoList(ent.getProductInfoList()
                                .stream()
                                .map(productInfo -> {
                                    ColorDTO colorDTO = AppUtil.mapperEntAndDto(productInfo.getColor(), ColorDTO.class);
                                    colorDTO.setProductInfoId(productInfo.getId());
                                    return colorDTO;
                                })
                                .collect(Collectors.toList())
                        );
                        return dto;
                    })
                    .collect(Collectors.toList());
            return productDtoList; // trả kết quả
        }
        return null;
    }
    //sản phẩm new
    @Override
    public List<ProductDto> findNew() {
        return productRepository.getProductNew()
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, ProductDto.class))
                .collect(Collectors.toList());
    }
    //sản phẩm sale
    @Override
    public List<ProductDto> findSale() {
        return productRepository.getProductSale()
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, ProductDto.class))
                .collect(Collectors.toList());
    }


    //Lưu và update SP
    @Override
    public ProductDto saveOrUpdate(HttpServletRequest request, Object object) {
        ProductDto productDto = (ProductDto) object;
        String image = null;

        if (productDto.getFileImg() != null){
            try {
                File newFile = new File("F:\\DoAn_SpringBoots\\do-an-web\\src\\assets\\style\\img\\"+productDto.getFileImg().getOriginalFilename());
                FileOutputStream fileOutputStream;
                fileOutputStream=new FileOutputStream(newFile);
                fileOutputStream.write(productDto.getFileImg().getBytes());
                fileOutputStream.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
            //image =productDto.setFileImg( "assets/style/img/"+getOriginalFilename());
        }
        //entity
        Product product;
        if (productDto != null) {
            // dữ liệu ms về type  va brand
            ProductType productType = AppUtil.NVL(productDto.getProductTypeId()) == 0L ? null :
                    productTypeRepository.findById(productDto.getProductTypeId()).orElse(null);
            Brand brand = AppUtil.NVL(productDto.getBrandId()) == 0L ? null :
                    brandRepository.findById(productDto.getBrandId()).orElse(null);

            //Lưu mới product
            if (AppUtil.NVL(productDto.getId()) == 0L) {
                product = AppUtil.mapperEntAndDto(productDto, Product.class);
                product.setCreatedDate(new Date());
                product.setUpdatedDate(new Date());
                product.setProductType(productType);
                product.setBrand(brand);
                product.setImage(image);
            }else {
                product = productRepository.findById(productDto.getId()).orElse(null);
                if (product != null){
                    Product data = AppUtil.mapperEntAndDto(productDto, Product.class);
                    data.setId(product.getId());
                    data.setUpdatedDate(new Date());
                    data.setProductType(productType); // chỗ này do có thể thay đôi nên set lại thôi, data ms lấy ở trên r
                    data.setBrand(brand);
                   data.setImage(image);
                    product = data;
                }
            }
            return AppUtil.mapperEntAndDto(productRepository.save(product), ProductDto.class);
        }
        return null;
    }

    // tìm kiếm sp theo Id
    @Override
    public ProductDto findById(HttpServletRequest request, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null){
            ProductDto productDto = AppUtil.mapperEntAndDto(product, ProductDto.class);
            productDto.setColoList(product.getProductInfoList()
                    .stream()
                    .map(productInfo -> {
                        ColorDTO colorDTO = AppUtil.mapperEntAndDto(productInfo.getColor(), ColorDTO.class);
                        colorDTO.setProductInfoId(productInfo.getId());
                        return colorDTO;
                    })
                    .collect(Collectors.toList())
            );
            return productDto;
        }
        return null;
    }
    //tìm kiếm sản phẩm theo hãng
    @Override
    public List<ProductDto> findAllBrand(HttpServletRequest request, Long id) {
        return productRepository.getAllByBrands(id)
                .stream()
                .map(obj ->{
                    ProductDto dto = AppUtil.mapperEntAndDto(obj, ProductDto.class);
                    dto.setColoList(obj.getProductInfoList()
                            .stream()
                            .map(
                                    productInfo -> {
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

    //lọc sản phẩm theo hãng và thể loại
    @Override
    public List<ProductDto> findFillter(HttpServletRequest request, ProductDto dto) {
        return productRepository.getProductByBrandAndProductType(dto.getBrandId(), dto.getProductTypeId())
                .stream()
                .map(obj ->{
                    ProductDto productDto = AppUtil.mapperEntAndDto(obj, ProductDto.class);
                    productDto.setColoList(obj.getProductInfoList()
                            .stream()
                            .map(
                                    productInfo -> {
                                        ColorDTO colorDTO = AppUtil.mapperEntAndDto(productInfo.getColor(), ColorDTO.class);
                                        colorDTO.setProductInfoId(productInfo.getId());
                                        return colorDTO;
                                    })
                            .collect(Collectors.toList())
                    );
                    return productDto;
                }).
                        collect(Collectors.toList());
    }


    @Override
    public Boolean delete(HttpServletRequest request, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            return true;
            //xoa
        }
        return false;
    }

    //tìm kiếm sp theo người dùng nhập
    @Override
    public List<ProductDto> search(HttpServletRequest request, ProductDto dto) {
        return productRepository.search(dto.getName().toLowerCase())
                .stream()
                .map(obj ->{
                    ProductDto productDto = AppUtil.mapperEntAndDto(obj, ProductDto.class);
                    productDto.setColoList(obj.getProductInfoList()
                            .stream()
                            .map(
                                    productInfo -> {
                                        ColorDTO colorDTO = AppUtil.mapperEntAndDto(productInfo.getColor(), ColorDTO.class);
                                        colorDTO.setProductInfoId(productInfo.getId());
                                        return colorDTO;
                                    })
                            .collect(Collectors.toList())
                    );
                    return productDto;
                }).
                        collect(Collectors.toList());
    }

    //Chi tiết product
    @Override
    public ProductDto searchDetailProduct(HttpServletRequest request, Long id) {
        Product product = productRepository.findById(id).orElse(null);// 2 kiểu khác nhau sao convert
        if(product !=null){
            //id product khác null mới truyền vào
//            return productRepository.selectproductDetail(product.getId())
//                    .stream()
//                    .map(detail -> AppUtil.mapperEntAndDto(detail,ProductInfoDTO.class))
//                    .collect(Collectors.toList());
            return AppUtil.mapperEntAndDto(product, ProductDto.class);
        }
        return null;

    }
}
