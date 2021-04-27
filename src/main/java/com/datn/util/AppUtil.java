package com.datn.util;

import org.modelmapper.ModelMapper;

import java.util.List;

public class AppUtil {
    public static <T> T mapperEntAndDto(Object source, Class<T> result){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(source, result);
    }

    public static Boolean isNullOrEmpty(String s){
        if (s == null || s.isEmpty() || "".equals(s) || s.length() == 0){
            return true;
        }
        return false;
    }

    public static Boolean isNullOrEmpty(List list){
        if (list == null || list.isEmpty() || list.size() == 0){
            return true;
        }
        return false;
    }

    public static Long NVL(Long n){
        return n == null ? 0L : n;
    }

    public static Integer NVL(Integer n){
        return n == null ? 0 : n;
    }

    public static Double NVL(Double n){
        return n == null ? 0.0 : n;
    }
}
