package com.datn.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
public class ProductTypeDTO extends BaseDto{
    private String name;

    public ProductTypeDTO() {
    }
}
