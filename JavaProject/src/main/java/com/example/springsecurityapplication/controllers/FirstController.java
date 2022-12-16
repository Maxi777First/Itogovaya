package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.repositories.CartRepository;
import com.example.springsecurityapplication.repositories.OrderRepository;
import com.example.springsecurityapplication.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.springsecurityapplication.services.ProductService;
import com.example.springsecurityapplication.models.Product;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.springsecurityapplication.repositories.ProductRepository;


@Controller
public class FirstController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Autowired

    public FirstController(ProductRepository productRepository, OrderRepository orderRepository, CartRepository cartRepository, ProductService productService) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("/main")
    public String Show_main(Model model) {
        return "/main";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
            return "/contact";
    }

    @GetMapping("/index")
    public String index(Model model){
            model.addAttribute("products", productService.getAllProduct());
            return "/index";
    }

    @GetMapping("/search")
    public String search(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "/index";
    }

    @PostMapping("/search")
    public String productSearch(@RequestParam(name = "search") String search, @RequestParam(name = "ot") String ot, @RequestParam(name = "do") String Do, @RequestParam(value = "price", required = false, defaultValue = "") String price, Model model){
        if(!ot.isEmpty() & !Do.isEmpty()) {
            if (!price.isEmpty()) {
                if (price.equals("sorted_by_ascending_price")) {
                        model.addAttribute("products", productRepository.findByTitleOrderByPrice(search, Float.parseFloat(ot), Float.parseFloat(Do)));
                } else if (price.equals("sorted_by_descending_price")) {
                        model.addAttribute("products", productRepository.findByTitleOrderByPriceDesc(search, Float.parseFloat(ot), Float.parseFloat(Do)));
                }
            }
            else {
                model.addAttribute("products", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThan(search, Float.parseFloat(ot), Float.parseFloat(Do)));
            }
        } else {
            model.addAttribute("products",productRepository.findByTitleContainingIgnoreCase(search));
        }
        model.addAttribute("value_search", search);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", Do);
        return "/index";
    }

}
