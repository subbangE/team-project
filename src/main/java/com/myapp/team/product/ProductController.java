package com.myapp.team.product;

import com.myapp.team.option.Option;
import com.myapp.team.option.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
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
/*    @GetMapping("/product")
    public String showProductsPage(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }*/

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
        for (Product product : products) {
            List<Option> options = optionService.selectOptionListByProduct(product.getProductNo());
            product.setOptions(options);
        }
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

        // 이미지 파일 저장
        MultipartFile image = product.getProductImageFile();
        if (image != null && !image.isEmpty()) {
        Date createDate = new Date();
        String storeFileName = createDate.getTime() + "." + image.getOriginalFilename();
        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectory(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storeFileName), StandardCopyOption.REPLACE_EXISTING);
            }
            // 저장된 이미지 경로를 설정
            product.setProductImageName(storeFileName);
            product.setProductImagePath("/images/" + storeFileName);
        } catch (Exception ex) {
            System.out.println("이이이이미미이이이지지지지이이없다 " + ex.getMessage());
            }
        }

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
    public String updateProductPage(@PathVariable("id") int id, Model model) {
        Product product = productService.findProductById(id);
        List<Option> options = optionService.selectOptionListByProduct(product.getProductNo());
        product.setOptions(options);
        model.addAttribute("product", product);
        return "update";
    }


    // UPDATE
//    @PostMapping("/update/{id}")
//    public String updateProduct(@PathVariable("id") int id, @ModelAttribute Product product, Option option) {
//        option.setOptionNo(id);
//        product.setProductNo(id);
//        optionService.updateOption(option);
//        productService.updateProduct(product);
//        return "redirect:/prod/list";
//    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") int id, @ModelAttribute Product product, BindingResult result, RedirectAttributes ra) {
        // PathVariable로 전달된 id를 Product 객체에 설정
        product.setProductNo(id);

        // Product 먼저 업데이트
        productService.updateProduct(product);
        // 옵션 업데이트
        for (Option option : product.getOptions()) {
            option.setProductId(id);
            optionService.updateOption(option);
        }

        // 결과 메시지를 RedirectAttributes에 추가
        ra.addFlashAttribute("manage_result", product.getProductName());

        System.out.println("update test = " + product);

        // 제품 목록 페이지로 리디렉션
        return "redirect:/prod/list";
    }




    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        optionService.deleteOption(id);
        productService.deleteProduct(id);
        return "redirect:/prod/list";
    }

    @PostMapping("/option/delete")
    public String deleteOption(int optionNo) {
        optionService.deleteOption(optionNo);
        return "redirect:/prod/list";
    }

}