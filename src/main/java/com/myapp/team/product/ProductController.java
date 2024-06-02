package com.myapp.team.product;

import com.myapp.team.cart.controller.CartController;
import com.myapp.team.cart.controller.CartService;
import com.myapp.team.cart.model.Cart;
import com.myapp.team.option.Option;
import com.myapp.team.option.OptionService;
import com.myapp.team.user.model.User;
import com.myapp.team.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductController {

    private final ProductService productService;
    private final OptionService optionService;
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public ProductController(ProductService productService, OptionService optionService, CartService cartService, UserService userService) {
        this.productService = productService;
        this.optionService = optionService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        for (Product product : products) {
            List<Option> options = optionService.selectOptionListByProduct(product.getProductNo());
            product.setOptions(options);
        }
        model.addAttribute("products", products);
        return "product/product";
    }

    // 상품 번호로 조회 (상품 상세 페이지)
    @GetMapping("/{id}")
//    @ResponseBody
    public Product getProductById(@PathVariable int id) {
        return productService.findProductById(id);
    }

    // 등록된 전체 상품을 리스트로 보여주는 곳 (관리자)
    @GetMapping("/list")
    public String listProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        // 상품별 옵션 조회하면서
        for (Product product : products) {
            List<Option> options = optionService.selectOptionListByProduct(product.getProductNo());
            product.setOptions(options);
        }
        model.addAttribute("products", products);
        return "product/list";
    }

    // Create Page
//    @GetMapping("/insert/{id}")
//    public String createProductPage(@PathVariable int id, Model model) {
//        Product product = productService.findProductById(id);
//        model.addAttribute("product", product);
//        return "product/insert";
//    }

    // Create Page
    @GetMapping("/insert")
    public String createProductPage(Model model) throws Exception {
        model.addAttribute("product", new Product());
        return "product/insert";
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
        return "product/update";
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
        // 제품 목록 페이지로 리다이렉션
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


    // 상품 상세 페이지
//    @GetMapping("/detail/{id}")
//    public String detailProduct(@PathVariable("id") int id, Model model) {
//        Product product = productService.findProductById(id);
//        List<Option> options = optionService.selectOptionListByProduct(product.getProductNo());
//        product.setOptions(options);
//        model.addAttribute("product", product);
//        return "product/detail";
//    }

    @GetMapping("/detail/{id}")
    public String detailProduct(@PathVariable("id") int id, Model model, Principal principal) {
        Product product = productService.findProductById(id);
        List<Option> options = optionService.selectOptionListByProduct(product.getProductNo());
        product.setOptions(options);
        model.addAttribute("product", product);

        // 현재 로그인한 사용자의 userNo를 가져옴
//        String userNo = principal.getName();
//        model.addAttribute("userNo", userNo);

        return "product/detail";
    }

    // 장바구니로 이동 페이지 -> 필요없음
//    @GetMapping("/cart")
//    public String cartPage() {
//        return "cart/cart";
//    }

    @GetMapping("/prod/addcart/{userId}")
    public String addCart(@RequestParam("productNo") int productNo, @RequestParam("optionNo") int optionNo, @RequestParam("cartCount") int cartCount, Principal principal) {
        String userId = principal.getName();  // 현재 로그인한 사용자의 userId를 가져옴

        Cart cart = new Cart();
        cart.setUserNo(Integer.parseInt(userId));  // userId를 사용하여 설정
        cart.setProductNo(String.valueOf(productNo));
//        cart.setOptionNo(optionNo);
        cart.setCartCount(cartCount);

        cartService.addCart(cart);  // 장바구니에 추가

        return "redirect:/prod/addcart/" + userId;  // /cart/{userId}로 리디렉션
    }
}