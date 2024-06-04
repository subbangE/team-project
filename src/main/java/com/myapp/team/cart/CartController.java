package com.myapp.team.cart;

import com.myapp.team.option.Option;
import com.myapp.team.product.Product;
import com.myapp.team.product.ProductMapper;
import com.myapp.team.user.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;

    @Autowired
    public CartController(CartService cartService, CartMapper cartMapper, ProductMapper productMapper, UserMapper userMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
        this.productMapper = productMapper;
        this.userMapper = userMapper;
    }

    @GetMapping("/cart/mycart")
    public void getMyCart(HttpSession session, Model model) {
        session.removeAttribute("orderItem"); //주문 요청 시마다 주문목록 session 갱신

        String loginMember = (String) session.getAttribute("loginMember");
        if(loginMember != null) {
            List<Cart> memberCart = cartMapper.getCurrentCart(loginMember);
            model.addAttribute("loginMember", loginMember);
            model.addAttribute("memberCart", memberCart);
        }
    }

    @PostMapping(value="/cart/add", produces="application/json; charset=UTF-8")
    @ResponseBody
    public String addToCart(HttpServletRequest request, HttpSession session) {
        String[] optionNoArr = request.getParameterValues("orderOptionNo");
        String[] optionQtArr = request.getParameterValues("orderOptionQt");

        String result = "";

        // Check if optionNoArr or optionQtArr are null
        if (optionNoArr == null || optionQtArr == null) {
            return "Option number array or option quantity array must not be null";
        }

        String loginMember = (String) session.getAttribute("loginMember");
        Integer userNo = userMapper.findUserNoByUsername(loginMember); // 사용자 ID로 user_no 조회

        if (userNo == null) {
            return "Invalid user ID: " + loginMember;
        }

        LoopA:
        for (int i = 0; i < optionNoArr.length; i++) {
            Cart cart = new Cart(); // cart item

            Integer prodNoByOptionNo = productMapper.searchProdNoByOptionNo(Integer.parseInt(optionNoArr[i]));
            if (prodNoByOptionNo == null) {
                return "Invalid option number: " + optionNoArr[i];
            }

            Option optionInfoByOptionNo = cartMapper.searchOptionInfoByOptionNo(Integer.parseInt(optionNoArr[i]));
            Product productInfoByOptionNo = cartMapper.searchProductInfoByOptionNo(Integer.parseInt(optionNoArr[i]));

            cart.setProductNo(prodNoByOptionNo);
            cart.setOptionNo(Integer.parseInt(optionNoArr[i]));
            cart.setProductCount(Integer.parseInt(optionQtArr[i]));
            cart.setOption(optionInfoByOptionNo);
            cart.setProduct(productInfoByOptionNo);
            cart.setUserNo(userNo); // userNo 설정
            cart.setUserId(loginMember); // userId 설정

            List<Cart> memberCart = cartMapper.getCurrentCart(loginMember); // DB에 저장된 회원의 기존 장바구니 호출

            if (memberCart == null) {
                memberCart = new ArrayList<>();
                memberCart.add(cart);
                cartMapper.addToCart(cart);
            } else {
                for (int j = 0; j < memberCart.size(); j++) {
                    if (memberCart.get(j).getOptionNo() == Integer.parseInt(optionNoArr[i])) {
                        int sum = memberCart.get(j).getProductCount() > 0 ? (memberCart.get(j).getProductCount() + cart.getProductCount()) : cart.getProductCount();
                        memberCart.get(j).setProductCount(sum);
                        cart.setProductCount(sum);
                        cartMapper.updateQuantityInCart(loginMember, cart.getProductCount(), cart.getOptionNo());
                        session.setAttribute("dapalaCart", memberCart);
                        result = "이미 장바구니에 담겨 있는 상품입니다";
                        continue LoopA;
                    }
                }
                memberCart.add(cart);
                cartMapper.addToCart(cart);
            }

            session.setAttribute("dapalaCart", memberCart);
            session.setAttribute("countCartItem", memberCart.size());
        }

        if (result.isEmpty()) {
            result = "성공";
        }

        return "redirect:/cart/"+userNo;
    }



    /**
     * 장바구니 상품별 선택 수량 변경
     */
    @PostMapping(value="/cart/modify", produces="application/json; charset=UTF-8")
    @ResponseBody
    public void changeQuantity(@RequestBody Map<String, String> param, HttpSession session) {
        String productCount = param.get("productCount");
        String optionNo = param.get("optionNo");
        log.info("변경 수량 : {}", productCount);
        log.info("해당 옵션 : {}", optionNo);

        String loginMember = (String) session.getAttribute("loginMember");
        log.info("로그인 사용자: {}", loginMember);

        if (loginMember == null) {
            log.error("사용자가 로그인되어 있지 않습니다.");
            return;
        }

        Integer userNo = userMapper.findUserNoByUsername(loginMember);
        log.info("로그인 사용자 번호: {}", userNo);

        if (userNo == null) {
            log.error("유효하지 않은 사용자 ID: {}", loginMember);
            return;
        }

        // 회원용 장바구니
        List<Cart> memberCart = cartMapper.getCurrentCart(loginMember);
        for (int i = 0; i < memberCart.size(); i++) {
            if (Integer.parseInt(optionNo) == memberCart.get(i).getOptionNo()) {
                cartMapper.updateQuantityInCart(loginMember, Integer.parseInt(productCount), Integer.parseInt(optionNo));
                memberCart.get(i).setProductCount(Integer.parseInt(productCount));
            }
        }
        session.setAttribute("dapalaCart", memberCart);
    }
    /**
     * 장바구니 선택 상품 행 삭제
     */
    @PostMapping(value="/cart/delete", produces="application/json; charset=UTF-8")
    @ResponseBody
    public void removeCartItem(@RequestBody Map<String, String> param, HttpSession session) {
        String optionNo = param.get("optionNo");
        log.info("삭제 요청 옵션 : {}", optionNo);

        String loginMember = (String) session.getAttribute("loginMember");

         //회원용 장바구니
            List<Cart> memberCart = cartMapper.getCurrentCart(loginMember);
            log.info("회원용 장바구니 호출");
            for(int j=0; j < memberCart.size(); j++) {
                if(Integer.parseInt(optionNo) == memberCart.get(j).getOptionNo()) {
                    cartMapper.deleteItemFromCart(loginMember, Integer.parseInt(optionNo));
                }
            }
        }


    /**
     * 장바구니 선택 상품 목록 삭제
     */
    @PostMapping(value="/cart/deleteAll", produces="application/json; charset=UTF-8")
    @ResponseBody
    public void removeAllItems(HttpServletRequest request, HttpSession session) {
        String[] optionNoArr = request.getParameterValues("arr");

        String loginMember = (String) session.getAttribute("loginMember");

        for(int i=0; i < optionNoArr.length; i++) {
            String optionNo = optionNoArr[i];
            log.info("삭제 요청 옵션 : {}", optionNo);

            if(loginMember == null) { //비회원용 장바구니
                List<Cart> nonmemberCart = (List<Cart>) session.getAttribute("dapalaCart");
                log.info("비회원용 session 장바구니 호출");
                for(int j=0; j < nonmemberCart.size(); j++) {
                    if(Integer.parseInt(optionNo) == nonmemberCart.get(j).getOptionNo()) {
                        nonmemberCart.remove(j);
                    }
                }
            } else { //회원용 장바구니
                List<Cart> memberCart = cartMapper.getCurrentCart(loginMember);
                log.info("회원용 장바구니 호출");
                for(int j=0; j < memberCart.size(); j++) {
                    if(Integer.parseInt(optionNo) == memberCart.get(j).getOptionNo()) {
                        cartMapper.deleteItemFromCart(loginMember, Integer.parseInt(optionNo));
                    }
                }
            }
        }

        //header에 변경된 상품 개수 반영
        if(loginMember == null) {
            List<Cart> nonmemberCart = (List<Cart>) session.getAttribute("dapalaCart");
            log.info("선택 상품 삭제 완료된 비회원용 session 장바구니 : {}", nonmemberCart);
            session.setAttribute("countCartItem", nonmemberCart.size());
        } else {
            List<Cart> memberCart = cartMapper.getCurrentCart(loginMember);
            log.info("선택 상품 삭제 완료된 회원용 장바구니 : {}", memberCart);
            session.setAttribute("countCartItem", memberCart.size());
        }
    }
}
