package com.myapp.team.cart;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

//    @Select("SELECT * FROM Cart WHERE userId = #{userId}")
//    @Results({
//        @Result(property = "cartId", column = "cartId"),
//        @Result(property = "userId", column = "userId"),
//        @Result(property = "productId", column = "productId"),
//        @Result(property = "quantity", column = "quantity"),
//        @Result(property = "product", column = "productId",
//            one = @One(select = "com.myapp.team.product.ProductMapper.findProductById"))
//    })
    List<Cart> findCartsByUserId(int userId);

//    @Insert("INSERT INTO Cart(userId, productId, quantity) VALUES(#{userId}, #{productId}, #{quantity})")
//    @Options(useGeneratedKeys = true, keyProperty = "cartId")
    void insertCart(Cart cart);

//    @Update("UPDATE Cart SET quantity = #{quantity} WHERE cartId = #{cartId}")
    void updateCart(Cart cart);

//    @Delete("DELETE FROM Cart WHERE cartId = #{cartId}")
    void deleteCart(int cartId);

    List<Cart> findCartsByUserNo(int userNo);
}
