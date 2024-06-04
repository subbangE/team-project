package com.myapp.team.cart;

import com.myapp.team.option.Option;
import com.myapp.team.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

//    @GetMapping("/add/{userNo}")
//    public String viewUserCart(@PathVariable("userNo") int userNo, Model model) {
//        List<Cart> carts = cartService.findCartsByUserNo(userNo);
//        model.addAttribute("carts", carts);
//        return "cart";
//    }

    // 상품 상세 -> 장바구니 (/cart/add)
//    @PostMapping("/add/{userNo}")
//    public String addToCart(@PathVariable("userNo") int userNo, @ModelAttribute Cart cart) {
//        cartService.insertCart(cart);
//        return "redirect:/cart/" + cart.getUserNo();
//    }

    @PostMapping("/add/{userNo}")
    public String addToCart(@ModelAttribute("product") Product product, @ModelAttribute("option") Option option,
                            @PathVariable("userNo") int userNo,
                            RedirectAttributes redirectAttributes) {

        Cart cart = new Cart();
        cart.setUserNo(userNo);
        cart.setProductName(product.getProductName());
        cart.setProductPrice(product.getProductPrice());
        cart.setOptions(option.getOptionName());
        cart.setCartCount(1); // 기본 값 설정

        String productName = product.getProductName();

        if (productName == null || productName.isEmpty()) {
            // 로그를 출력하거나 예외를 던집니다.
            System.err.println("Product name 없음 다시 해 !!");
            throw new IllegalArgumentException("오류 !!!!! 다시해 !!");
        }
        cartService.insertCart(cart);
        return "redirect:/cart/add/" + userNo;
    }

    @GetMapping("/add/{userNo}")
    public String addToCart(@PathVariable("userNo") int userNo, @ModelAttribute Cart cart, Model model) {

        List<Cart> carts = cartService.findAllCartItem(userNo);
        model.addAttribute("carts", carts);
        return "cart";
    }

    // 수정
    @PostMapping("/update")
    public String updateCart(Cart cart) {
        cartService.updateCart(cart);
        return "redirect:/cart/" + cart.getUserNo();
    }

    // 삭제
    @PostMapping("/delete/{cartNo}")
    public String deleteCart(@PathVariable("cartNo") int cartNo) {
        Cart cart = cartService.findCartsByUserNo(cartNo).get(0);
        cartService.deleteCart(cartNo);
        return "redirect:/cart/" + cart.getUserNo();
    }
}