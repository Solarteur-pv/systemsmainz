package dev.yukado.systemsmainz.api.controller;

import dev.yukado.systemsmainz.entity.OwnCategory;
import dev.yukado.systemsmainz.service.own.OwnCategoryService;
import dev.yukado.systemsmainz.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin/own_category")
public class OwnCategoryAdminController {

    private final OwnCategoryService service;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public OwnCategoryAdminController(OwnCategoryService service,
                                      UserDetailsService userDetailsService,
                                      UserService userService) {
        this.service = service;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model, Principal principal) {

        // --- Admin Header Daten ---
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        String userName = "A" + id1;

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", id1);

        // --- Kategorien ---
        model.addAttribute("categories", service.findAll());
        model.addAttribute("category", new OwnCategory());

        return "admin/own_category";
    }

    @PostMapping
    public String create(OwnCategory category) {
        service.create(category);
        return "redirect:/admin/own_category";
    }
}
