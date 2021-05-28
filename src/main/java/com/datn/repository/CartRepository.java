package com.datn.repository;

import com.datn.entity.Cart;
import com.datn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
