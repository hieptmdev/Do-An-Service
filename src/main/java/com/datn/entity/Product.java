package com.datn.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Sản phẩm
 */
@Entity
@Table
@Getter
@Setter
public class Product extends BaseEntity{
    @Column(nullable = false)
    private String name;
    private String code;
    @Column(nullable = false)
    private Long priceSell; // giá bán
    private Double sale; // giảm giá
    private String image;
    //1 sản phẩm có nhiều list j đây mài // có nhiều thông tin // vd như giày thì có nhiều cỡ, màu,..
    @OneToMany(mappedBy = "product")
    private List<ProductInfo> productInfoList;
    //nhiều sp có 1 type thôi à // loại sp: shop m bán nhiều thứ như giày, túi, quần áo,... or phân loại giày như thể thao, thời trang,....
    // thế 1 giày thì có nhiều loại ah :)) :v
    //có nghĩa là: 1 loại thì có nhiều sp đúngg k / phải vd như m có 10 đôi giày đều thuocj loại sneaker
    @ManyToOne
    private ProductType productType; // loại sản phẩm // ở đây là 1 object, khi truy vấn = jpa sẽ nhận đc toàn bộ giá trị theo khóa này

    @ManyToOne
    private Brand brand; // hãng sản xuất

    public Product(){}
}
