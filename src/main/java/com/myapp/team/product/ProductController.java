package com.myapp.team.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 페이지
    @GetMapping("/product")
    public String showProductsPage(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }

//    // 상품 리스트
//    @GetMapping("/allList")
//    @ResponseBody
//    public List<Product> getAllProducts() {
//        return productService.findAllProducts();
//    }

    // Product Detail Page
    @GetMapping("/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable int id) {
        return productService.findProductById(id);
    }

    // Create Page
    @GetMapping("/create")
    public String createProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "create";
    }

    // Create
    @PostMapping("/create")
    public String createProduct(@ModelAttribute Product product, BindingResult result) {

        if (result.hasErrors()) {
            return "create";
        }
        productService.createProduct(product);

        return "redirect:/prod/product";
    }

    // Update Page
    @GetMapping("/update/{id}")
    public String updateProductPage(@PathVariable int id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "update";
    }

    // Update
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute Product product) {
        product.setProductNo(id);
        productService.updateProduct(product);
        return "redirect:/prod/product";
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/prod/product";
    }
}