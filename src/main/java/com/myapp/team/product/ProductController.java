package com.myapp.team.product;

import com.myapp.team.option.Option;
import com.myapp.team.option.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductController {

    private final ProductService productService;
    private final OptionService optionService;

    @Autowired
    public ProductController(ProductService productService, OptionService optionService) {
        this.productService = productService;
        this.optionService = optionService;
    }

    // Product Page
    @GetMapping("/product")
    public String showProductsPage(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }

@GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        for (Product product : products) {
            List<Option> options = optionService.selectOptionListByProduct(product.getProductNo());
            product.setOptions(options);
        }
        model.addAttribute("products", products);
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
//    @PostMapping("/insert")
//    public String insertProduct(Product product, RedirectAttributes ra) {
//        productService.insertProduct(product);
//
//        // Product 객체의 options 필드에 있는 각 Option 객체를 데이터베이스에 저장
////        for (Option option : product.getOptions()) {
////            option.setProduct(product);  // Option 객체가 속한 Product 객체를 설정
////            optionService.insertOption(option);  // Option 객체를 데이터베이스에 저장
////        }
//
//        ra.addFlashAttribute("manage_result", product.getProductName());
//        return "redirect:/prod/list";
//    }

    @PostMapping("/insert")
    public String insertProduct(Product product, @RequestParam("optionName") List<String> optionNames,
                                @RequestParam("optionValue") List<String> optionValues,
                                @RequestParam("optionCount") List<Integer> optionCounts,
                                RedirectAttributes ra) {
        List<Option> options = new ArrayList<>();
        for (int i = 0; i < optionNames.size(); i++) {
            Option option = new Option();
            option.setOptionName(optionNames.get(i));
            option.setOptionValue(optionValues.get(i));
            option.setOptionCount(optionCounts.get(i));
            options.add(option);
        }
        product.setOptions(options);
        productService.insertProduct(product);

        ra.addFlashAttribute("manage_result", product.getProductName());
        return "redirect:/prod/list";
    }

    @PostMapping("/insertOption")
    public String insertOption(Product product, Option option) {
        optionService.insertOption(product, option);
        return "redirect:/prod/list";
    }

    // Update Page
    @GetMapping("/update/{id}")
    public String updateProductPage(@PathVariable int id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "update";
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
        optionService.deleteOption(id);
        productService.deleteProduct(id);
        return "redirect:/prod/list";
    }

    @PostMapping("/option/delete")
    public String deleteOption(int optionNo) {
        optionService.deleteOption(optionNo);
        return "redirect:/prod/list";
    }

//    @GetMapping("/products")
//    public List<Product> getProductsByCategory(@RequestParam String category) {
//        return productService.getProductsByCategory(category);
//    }

}