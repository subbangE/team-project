package com.myapp.team.product;

import com.myapp.team.option.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    @Autowired
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

//    public void insertProduct(Product product, Option option) {
//        if (product == null || StringUtils.isEmpty(product.getProductName())) {
//            throw new IllegalArgumentException("Product name must not be null or empty");
//        }
//        productMapper.insertProduct(product, option);
//    }

    @Transactional
    public void insertProduct(Product product) {
        // Product 삽입
        productMapper.insertProduct(product);

        // 삽입된 Product의 productNo가 제대로 설정되었는지 확인
        if (product.getProductNo() == null || product.getProductNo() == 0) {
            throw new RuntimeException("Product ID is not generated correctly.");
        }

        System.out.println("Generated Product ID: " + product.getProductNo());

        // 각 옵션에 productNo 설정 후 삽입
        for (Option option : product.getOptions()) {
            option.setProductNo(product.getProductNo()); // 방금 삽입한 Product의 ID 설정
            System.out.println("Inserting Option with Product ID: " + option.getProductNo());
            productMapper.insertOption(option);
        }
    }

    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

//    @Transactional
    public void deleteProduct(int productNo) {
        // 해당 제품 번호 옵션 지우기
        productMapper.deleteOptions(productNo);
        // 해당 제품 번호 제품 지우기
        productMapper.deleteProduct(productNo);
    }

//    public List<Product> getProductsByCategory(String categoryName) {
//        return productMapper.selectProductsByCategory(categoryName);
//    }
}
