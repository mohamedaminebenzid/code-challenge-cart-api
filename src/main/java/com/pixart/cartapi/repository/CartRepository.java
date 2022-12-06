package com.pixart.cartapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pixart.cartapi.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
