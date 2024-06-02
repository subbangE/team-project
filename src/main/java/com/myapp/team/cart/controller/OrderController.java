package com.myapp.team.cart.controller;

import com.myapp.team.cart.model.Order;
import com.myapp.team.cart.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    // 이건 뭔 파일 ??

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderMapper.findAll();
        model.addAttribute("orders", orders);
        return "cart/orders";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Long id, Model model) {
        Order order = orderMapper.findById(id);
        model.addAttribute("order", order);
        return "cart/orderDetails";
    }

    @GetMapping("/create")
    public String createOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "cart/createOrder";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order) {
        orderMapper.insert(order);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderMapper.delete(id);
        return "redirect:/orders";
    }

    @PostMapping("/complete")
    public String completeOrder(@ModelAttribute Order order, Model model) {
        model.addAttribute("order", order);
        return "cart/orderComplete";
    }
}
