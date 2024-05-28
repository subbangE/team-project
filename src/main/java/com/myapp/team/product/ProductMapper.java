package com.myapp.team.product;

import org.apache.ibatis.annotations.*;

import java.util.List;
// repository는 JPA

@Mapper
public interface ProductMapper {

    List<Product> findAllProducts();

    Product findProductById(int product_no);

    void createProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(int product_no);
}
