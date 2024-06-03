package com.myapp.team.cart;

import com.myapp.team.product.Product;
import com.myapp.team.product.ProductService;
import com.myapp.team.user.config.CustomUserDetails;
import com.myapp.team.user.model.User;
import com.myapp.team.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;
    private final CartMapper cartMapper;

    @Autowired
    public CartController(CartService cartService, ProductService productService, UserService userService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
        this.cartMapper = cartMapper;
    }

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping
//    public String viewCart(Model model, Principal principal) {
//        int userNo = Integer.parseInt(principal.getName());
//        List<Cart> carts = cartService.findCartsByUserId(userNo);
//        model.addAttribute("carts", carts);
//        return "cart";
//    }

//    @GetMapping("/{userNo}")
//    public String viewCart(@PathVariable("userNo") int userNo, Model model) {
//        List<Cart> carts = cartService.findCartsByUserId(userNo);
//        model.addAttribute("carts", carts);
//        return "cart";
//    }

//    @GetMapping
//    public String viewCart(Model model, Principal principal) {
//        if (principal != null) {
//            int userNo = Integer.parseInt(principal.getName());
//            List<Cart> carts = cartService.findCartsByUserNo(userNo);
//            model.addAttribute("carts", carts);
//            model.addAttribute("userNo", userNo);
//        }
//        return "cart";
//    }

    @GetMapping("/{userNo}")
    public String viewUserCart(@PathVariable("userNo") int userNo, Model model) {
        List<Cart> carts = cartService.findCartsByUserNo(userNo);
        model.addAttribute("carts", carts);
        model.addAttribute("userNo", userNo);
        return "cart";
    }

//    @PostMapping("/add")
//    public String addToCart(Cart cart) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        int userNo = userDetails.getUserNo();
//        cart.setUserNo(userNo);
//        //cartService.insertCart(cart);
//        System.out.println("Redirecting to: /cart/" + userNo);
//        return "redirect:/cart/" + userNo;
//    }

//    @PostMapping("/add")
//    public String addToCart(@RequestParam("productName") String productName,
//                            @RequestParam("productPrice") int productPrice,
//                            @RequestParam("options") String options,
//                            Model model, Cart cart) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        int userNo = userDetails.getUserNo();
//        cart.setUserNo(userNo);
//
//        return "redirect:/cart/" + userNo;
//    }


    @PostMapping("/update")
    public String updateCart(Cart cart) {
        cartService.updateCart(cart);
        return "redirect:/cart";
    }

    @PostMapping("/delete")
    public String deleteCart(@RequestParam int cartId) {
        cartService.deleteCart(cartId);
        return "redirect:/cart";
    }

    // 상품 -> 장바구니
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam("productName") String productName,
                                            @RequestParam("productPrice") int productPrice,
                                            @RequestParam("options") String options,
                                            Model model) {

        Cart cart = new Cart();
        cart.setProductName(productName);
        cart.setProductPrice(productPrice);
        cart.setOptions(options);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int userNo = userDetails.getUserNo();
        cart.setUserNo(userNo);

        cartMapper.insertCart(cart);

        return ResponseEntity.ok("Item added to cart");
    }
}
