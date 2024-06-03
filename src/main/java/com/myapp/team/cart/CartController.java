package com.myapp.team.cart;

import com.myapp.team.product.Product;
import com.myapp.team.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
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


    @GetMapping
    public String viewCart(Model model, Principal principal) {
        if (principal != null) {
            int userNo = Integer.parseInt(principal.getName());
            List<Cart> carts = cartService.findCartsByUserNo(userNo);
            model.addAttribute("carts", carts);
            model.addAttribute("userNo", userNo);
        }
        return "cart";
    }

    @GetMapping("/{userNo}")
    public String viewUserCart(@PathVariable("userNo") int userNo, Model model) {
        List<Cart> carts = cartService.findCartsByUserNo(userNo);
        model.addAttribute("carts", carts);
        model.addAttribute("userNo", userNo);
        return "cart";
    }

//    @PostMapping("/add")
//    public String addToCart(Cart cart, Principal principal) {
//        int userNo = Integer.parseInt(principal.getName());
//        cart.setUserNo(userNo);
//        cartService.insertCart(cart);
//        return "redirect:/cart";
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
}
