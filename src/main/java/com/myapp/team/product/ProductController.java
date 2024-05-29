package com.myapp.team.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Product Page
    @GetMapping("/product")
    public String showProductsPage(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }

    // Product Detail Page
    @GetMapping("/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable int id) {
        return productService.findProductById(id);
    }

    // 등록된 전체 상품을 리스트로 보여주는 곳 (관리자)
    @GetMapping("/list")
    public String listProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "list";
    }

    // Create Page
    @GetMapping("/insert/{id}")
    public String createProductPage(@PathVariable int id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "insert";
    }

    // Create Page
    @GetMapping("/insert")
    public String createProductPage(Model model) throws Exception {
        model.addAttribute("product", new Product());
        return "insert";
    }

    // CREATE
    @PostMapping("/insert")
    public String insertProduct(@ModelAttribute Product product, BindingResult result, RedirectAttributes ra) {

        productService.insertProduct(product);
        ra.addFlashAttribute("manage_result", product.getProductName()); //상품이름이 등록되었음을 알리는 경고창 띄우기위함
        return "list";
    }

    // Update Page
    @GetMapping("/update/{id}")
    public String updateProductPage(@PathVariable int id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "list";
    }

    // UPDATE
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute Product product) {
        product.setProductNo(id);
        productService.updateProduct(product);
        return "redirect:/prod/list";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/prod/list";
    }
}