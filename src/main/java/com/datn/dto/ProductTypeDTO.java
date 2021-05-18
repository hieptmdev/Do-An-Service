package com.datn.dto;

import com.datn.entity.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
public class ProductTypeDTO extends BaseDto{
    private String name;
    private String image;
    public ProductTypeDTO() {
    }
}
