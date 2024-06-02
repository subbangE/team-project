package com.myapp.team.cart.controller;

import com.myapp.team.cart.model.Payment;
import com.myapp.team.cart.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orderPayment")
public class PaymentController {

    @Autowired
    private PaymentMapper paymentMapper;

    @GetMapping
    public String getAllPayments(Model model) {
        List<Payment> payments = paymentMapper.findAll();
        model.addAttribute("payments", payments);
        return "cart/payments";
    }

    @GetMapping("/{id}")
    public String getPaymentById(@PathVariable Long id, Model model) {
        Payment payment = paymentMapper.findById(id);
        model.addAttribute("payment", payment);
        return "cart/paymentDetails";
    }

    @GetMapping("/create")
    public String createPaymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        return "cart/createPayment";
    }

    @PostMapping
    public String createPayment(@ModelAttribute Payment payment) {
        paymentMapper.insert(payment);
        return "redirect:/payments";
    }

    @GetMapping("/update/{id}")
    public String updatePaymentForm(@PathVariable Long id, Model model) {
        Payment payment = paymentMapper.findById(id);
        model.addAttribute("payment", payment);
        return "cart/updatePayment";
    }

    @PostMapping("/update")
    public String updatePayment(@ModelAttribute Payment payment) {
        paymentMapper.update(payment);
        return "redirect:/payments";
    }

    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id){
        paymentMapper.delete(id);
        return "redirect:/payments";
    }
}

