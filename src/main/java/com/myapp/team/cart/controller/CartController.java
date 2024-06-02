package com.myapp.team.cart.controller;

import com.myapp.team.cart.mapper.CartMapper;
import com.myapp.team.cart.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartMapper cartMapper; // CartMapper 의존성을 자동으로 주입받음
    private CartService cartService;

    // 특정 사용자의 장바구니 항목 조회
    @GetMapping("/{userNo}")
    public String getCartItems(@PathVariable("userNo") int userNo, Model model) {
        List<Cart> cartItems = cartMapper.findByUserNo(userNo);
        model.addAttribute("cartItems", cartItems);
        return "cart/cart"; // 'cart.html' 템플릿을 반환
    }

    // 장바구니 항목 추가 폼 보여주기
//    @GetMapping("/create")
//    public String showCreateForm(Model model) {
//        model.addAttribute("cart", new Cart());
//        return "cart/CreateCart";
//    }

    // 장바구니 항목 추가
//    @PostMapping("/create")
//    public String createCartItem(@RequestParam("cartNo") int cartNo,
//                                 @RequestParam("userNo") int userNo,
//                                 @RequestParam("productNo") String productNo,
//                                 @RequestParam("cartCount") int cartCount) {
//        Cart cart = new Cart(cartNo, userNo, productNo, cartCount);
//        cartMapper.insertCart(cart);
//        return "redirect:/cart/" + userNo;
//    }

    // 특정 장바구니 항목 조회 (수정 폼으로 이동)
    @GetMapping("/update/{cartNo}")
    public String showUpdateForm(@PathVariable int cartNo, Model model) {
        Cart cart = cartMapper.getCart(cartNo);
        model.addAttribute("cart", cart);
        return "cart/UpdateCart";
    }

    // 장바구니 항목 수정
    @PostMapping("/update/{cartNo}")
    public String updateCartItem(@PathVariable("cartNo") int cartNo,
                                 @RequestParam("productNo") String productNo,
                                 @RequestParam("cartCount") int cartCount,
                                 @RequestParam("userNo") int userNo) {
        cartMapper.updateCart(cartNo, productNo, cartCount);
        return "redirect:/cart/" + userNo;
    }

    // 장바구니 항목 삭제
    @PostMapping("/delete")
    public String deleteCartItem(@RequestParam int cartNo,
                                 @RequestParam int userNo) {
        cartMapper.deleteCart(cartNo);
        return "redirect:/cart/" + userNo;
    }

    @GetMapping("/cart/{userId}")
    public String viewCart(@PathVariable("userId") String userId, Model model) {
        List<Cart> cartItems = cartService.findCartByUserId(userId);
        model.addAttribute("cartItems", cartItems);
        return "cart/cart";
    }

}
