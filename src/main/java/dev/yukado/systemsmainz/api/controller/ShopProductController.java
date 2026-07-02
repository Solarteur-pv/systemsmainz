package dev.yukado.systemsmainz.api.controller;

import dev.yukado.systemsmainz.entity.OwnProduct;
import dev.yukado.systemsmainz.service.own.OwnProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop/products")
public class ShopProductController {

    private final OwnProductService productService;

    public ShopProductController(OwnProductService productService) {
        this.productService = productService;
    }

    // LISTE ALLER PRODUKTE
    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "shop/shop_products";
    }

    // PRODUKT-DETAILSEITE
    @GetMapping("/{id}")
    public String productDetails(@PathVariable Long id, Model model) {

        OwnProduct product = productService.findById(id);

        if (product == null) {
            return "redirect:/shop/products"; // fallback
        }

        model.addAttribute("product", product);
        return "shop/product_details"; // dein neues Template
    }
}

