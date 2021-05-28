package com.datn.repository;

import com.datn.entity.Cart;
import com.datn.entity.CartDetaill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetaill, Long> {
    List<CartDetaill> findAllByCart(Cart cart);
}
