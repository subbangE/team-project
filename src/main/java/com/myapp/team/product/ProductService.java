package com.myapp.team.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }


    // 등록된 전체 상품 리스트
    public List<Product> findAllProducts() {
        return productMapper.findAllProducts();
    }

    public Product findProductById(int productNo) {
        return productMapper.findProductById(productNo);
    }

    public void insertProduct(Product product) {
        if (product == null || StringUtils.isEmpty(product.getProductName())) {
            throw new IllegalArgumentException("Product name must not be null or empty");
        }
        productMapper.insertProduct(product);
    }

    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

//    @Transactional
    public void deleteProduct(int productNo) {
        productMapper.deleteProduct(productNo);
    }
}
