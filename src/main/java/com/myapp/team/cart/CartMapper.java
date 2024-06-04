package com.myapp.team.cart;

import com.myapp.team.option.Option;
import com.myapp.team.product.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {
    List<Cart> getCurrentCart(String userName);

    Cart getCartItemByOptionNo(String userName, int optionNo);

    int getMemberCartNo(String userName);

    List<Cart> getCartItemList(Cart cart);

    void addToCart(Cart cart);

    void updateQuantityInCart(String userId, int productCount, int optionNo);

    void deleteItemFromCart(String userId, int optionNo);

    Option searchOptionInfoByOptionNo(int optionNo);

    Product searchProductInfoByOptionNo(int optionNo);


}
