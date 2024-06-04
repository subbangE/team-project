package com.myapp.team.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartMapper cartMapper;

    @Autowired
    public CartService(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    // 사용자 번호로 장바구니 찾기
    public List<Cart> findCartsByUserNo(int userNo) {
        return cartMapper.findCartsByUserNo(userNo);
    }

    public List<Cart> findAllCartItem(int userNo) {
        return cartMapper.findAllCartItem(userNo);
    }

    // 장바구니 추가
    public void insertCart(Cart cart) {
//        cart.setCartCount(1); // 기본 값 설정
        cartMapper.insertCart(cart);
    }

    // 장바구니 수정 (체크박스)
    public void updateCart(Cart cart) {
        cartMapper.updateCart(cart);
    }

    // 장바구니 삭제 (체크박스)
    public void deleteCart(int cartNo) {
        cartMapper.deleteCart(cartNo);
    }
}