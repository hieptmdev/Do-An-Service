package com.datn.repository;

import com.datn.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ColorReponsitory extends JpaRepository<Color, Long> {
}
