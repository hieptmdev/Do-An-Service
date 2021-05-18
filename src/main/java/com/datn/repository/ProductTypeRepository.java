package com.datn.repository;

import com.datn.entity.Product;
import com.datn.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    @Query("select pt from ProductType pt where pt.productTypeParent.id is null")
    List<ProductType> getAllParent();

    @Query("select p from  Product p join  ProductType pt on p.productType.id = pt.id" +
            " where pt.id = :id or pt.productTypeParent.id = :id") // chu y dau cach :ok b
    List<Product> getAllByCategory(Long id);

}
