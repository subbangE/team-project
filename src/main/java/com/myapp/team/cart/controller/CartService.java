package com.myapp.team.cart.controller;

import com.myapp.team.cart.mapper.CartMapper;
import com.myapp.team.cart.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    public void addCart(Cart cart) {
        cartMapper.addCart(cart);
    }

    public List<Cart> findCartByUserId(String userNo) {
        return cartMapper.findCartByUserId(userNo);
    }

    }


