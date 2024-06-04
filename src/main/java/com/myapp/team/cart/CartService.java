package com.myapp.team.cart;

import com.myapp.team.option.Option;
import com.myapp.team.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private CartMapper cartMapper;

    @Autowired
    public CartService(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    public List<Cart> getCurrentCart(String userId) {
        return cartMapper.getCurrentCart(userId);
    }

    public Cart getCartItemByOptionNo(String userName, int optionNo) {
        return cartMapper.getCartItemByOptionNo(userName, optionNo);
    }

    public List<Cart> getCartItemList(Cart cart) {
        return cartMapper.getCartItemList(cart);
    }

    public Option searchOptionInfoByOptionNo(int optionNo) {
        return cartMapper.searchOptionInfoByOptionNo(optionNo);
    }

    public Product searchProductInfoByOptionNo(int optionNo) {
        return cartMapper.searchProductInfoByOptionNo(optionNo);
    }


    // 다른 메서드 추가 가능
}
