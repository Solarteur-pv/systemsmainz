package dev.yukado.systemsmainz.api.controller;

import dev.yukado.systemsmainz.entity.OwnProduct;
import dev.yukado.systemsmainz.service.own.OwnCategoryService;
import dev.yukado.systemsmainz.service.own.OwnProductService;
import dev.yukado.systemsmainz.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin/own_product")
public class OwnProductAdminController {

    private final OwnProductService productService;
    private final OwnCategoryService categoryService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public OwnProductAdminController(OwnProductService productService,
                                     OwnCategoryService categoryService,
                                     UserDetailsService userDetailsService,
                                     UserService userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model, Principal principal) {

        // --- WICHTIG: Admin-Header-Daten laden ---
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        String userName = "A" + id1;

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", id1);

        // --- Eigene Produkte ---
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());

        return "admin/own_product";
    }

    @PostMapping
    public String create(OwnProduct product, Principal principal, Model model) {

        productService.create(product);

        return "redirect:/admin/own_product";
    }
}
