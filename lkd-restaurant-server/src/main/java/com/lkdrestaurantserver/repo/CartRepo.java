package com.lkdrestaurantserver.repo;

import com.lkdrestaurantserver.entity.Cart;
import com.lkdrestaurantserver.entity.User;
import com.lkdrestaurantserver.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    Boolean existsByUserAndCartStatus(User user, CartStatus cartStatus);

    List<Cart> findAllByUserId(Long userId);

    Optional<Cart> findByUserAndCartStatus(User user, CartStatus cartStatus);
}
