package com.myapp.team.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartMapper cartMapper;

    @Autowired
    public CartService(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    public List<Cart> findCartsByUserId(int userId) {
        return cartMapper.findCartsByUserId(userId);
    }

    public List<Cart> findCartsByUserNo(int userNo) {
        return cartMapper.findCartsByUserNo(userNo);
    }

    @Transactional
    public void insertCart(Cart cart) {
        cartMapper.insertCart(cart);
    }

    @Transactional
    public void updateCart(Cart cart) {
        cartMapper.updateCart(cart);
    }

    @Transactional
    public void deleteCart(int cartId) {
        cartMapper.deleteCart(cartId);
    }
}
