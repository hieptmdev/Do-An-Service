package com.datn.service;

import com.datn.dto.*;
import com.datn.entity.Brand;
import com.datn.entity.ImageModel;
import com.datn.entity.Product;
import com.datn.entity.ProductType;
import com.datn.repository.BrandRepository;
import com.datn.repository.ProductRepository;
import com.datn.repository.ProductTypeRepository;
import com.datn.service.iservice.ProductService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Lấy all sản phẩm ko phân trang
     * @return
     */
    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> productDtoList;
        List<Product> productList = productRepository.findAll(); // lấy danh sách product dạng entity, findAll() - hàm sẵn do jpa cung cấp
        if (!AppUtil.isNullOrEmpty(productList)){ // kiểm tra productList != null
            productDtoList = productList.stream()
                    .map(ent -> AppUtil.mapperEntAndDto(ent, ProductDto.class)) // method mapperEntAndDto dùng để chuyển đổi data từ entity sang dto or ngược lại tùy theo mục đích
                    .collect(Collectors.toList()); // chuyển đổi productList sang list mới ở dạng dto
            return productDtoList; // trả kết quả
        }
        return null;
    }
    @Override
    public List<ProductDto> findNew() {
        return productRepository.getProductNew()
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, ProductDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductDto> findSale() {
        return productRepository.getProductSale()
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findAllBrand(HttpServletRequest request, Long id) {
        return productRepository.getAllByBrands(id)
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, ProductDto.class)).
                        collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findFillter(HttpServletRequest request, ProductDto dto) {
        return productRepository.getProductByBrandAndProductType(dto.getBrandId(), dto.getProductTypeId())
                .stream()
                .map(obj -> AppUtil.mapperEntAndDto(obj, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto saveOrUpdate(HttpServletRequest request, Object object) {
        ProductDto productDto = (ProductDto) object;
        //đây là mình đang khai báo cái image của product
        //cái đoạn này ok chưa b, copy nguyên cái test của bn thì chắc ok
        String image = null; // cái nàu chỉ là biến cục bộ trong hàm chứ có phải cảu product đâu @@
        if (productDto.getFileImg() != null){
        //cái if này k hiểu cho lắm b
            //nó ko có data thì nó get ra giá trị j,k có thì null chứ sao :((
            // uhm null, thì phỉa kiểm tra xem data bn gửi về có file ko chứ, ko có file thì lưu kiểu j
            //coi như nó có giá trị, nó nhảy vào try
            try {
                //nó sẽ lưu vào trong này,ok chưa; tiếp đi
                File newFile = new File("F:\\DoAn_SpringBoots\\do-an-web\\src\\assets\\style\\img\\"+productDto.getFileImg().getOriginalFilename());
                FileOutputStream fileOutputStream;
                fileOutputStream=new FileOutputStream(newFile);
                fileOutputStream.write(productDto.getFileImg().getBytes());
                fileOutputStream.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            //sau đó bạn gán giá trị image của product = với cái đường dẫn assets/style/img/
            //  assets/style/img/ + tên file mà bên fontend n gửi lên như này
            //VD : image = "assets/style/img/5.jpg" nó sẽ được lưu trong database như này đúng k

            image = "assets/style/img/"+productDto.getFileImg().getOriginalFilename();
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
                //thế thì cái chỗ này nó có cần setlaij Image k
                // ko set lai vào sao lưu
                //Vậy là tổng thẻ là coi như csai product này n k lỗi về phía
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

    @Override
    public List<ProductDto> search(HttpServletRequest request, ProductDto dto) {
        return productRepository.search(dto.getName().toLowerCase())
                .stream().map(product -> AppUtil.mapperEntAndDto(product, ProductDto.class))
                .collect(Collectors.toList());
    }

}
