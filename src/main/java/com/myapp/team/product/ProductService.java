package com.myapp.team.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Product> findAllProducts() {
        return productMapper.findAllProducts();
    }

    public Product findProductById(int product_no) {
        return productMapper.findProductById(product_no);
    }

    public void createProduct(Product product) {
        productMapper.createProduct(product);
    }

    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

    public void deleteProduct(int product_no) {
        productMapper.deleteProduct(product_no);
    }
}
