package com.datn.repository;

import com.datn.entity.Cart;
import com.datn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
@Transactional
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
    @Query(value = "SELECT MAX(id) FROM Cart c WHERE c.user_id IS NULL", nativeQuery = true)
    Long findCartId();
}
