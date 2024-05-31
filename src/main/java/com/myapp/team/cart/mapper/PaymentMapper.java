package com.myapp.team.cart.mapper;

import com.myapp.team.cart.entity.Payment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PaymentMapper {


    List<Payment> findAll();

    Payment findById(Long paymentNo);


    void insert(Payment payment);

    void update(Payment payment);


    void delete(Long paymentNo);
}
