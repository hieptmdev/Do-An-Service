package com.datn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandDTO extends BaseDto {
    private String name;
    private String code;
    private String image;
        public BrandDTO() {
    }
}
