package dev.yukado.systemsmainz.api.controller;

import dev.yukado.systemsmainz.service.own.OwnProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop/products")
public class ShopProductController {

    private final OwnProductService productService;

    public ShopProductController(OwnProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "shop/shop_products";
    }
}

