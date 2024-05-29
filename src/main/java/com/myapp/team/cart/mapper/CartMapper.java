package com.myapp.team.cart.mapper;

import com.myapp.team.cart.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper

public interface CartMapper {

    List<Cart> findByUserNo(int userNo);


    Cart getCart(int cartNo);


    void insertCart(Cart cart);


    void updateCart(@Param("cartNo") int cartNo, @Param("productNo") String productNo, @Param("cartCount") int cartCount);


    void deleteCart(int cartNo);
}
