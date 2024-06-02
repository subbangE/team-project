package com.myapp.team.cart.mapper;

import com.myapp.team.cart.model.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {


    List<Order> findAll();


    Order findById(Long ordersId);


    void insert(Order order);
    void update(Order order);
    void delete(Long ordersId);
}
