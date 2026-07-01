package dev.yukado.systemsmainz.api.controller;

import dev.yukado.systemsmainz.entity.OwnProduct;
import dev.yukado.systemsmainz.entity.OwnProductImage;
import dev.yukado.systemsmainz.service.own.OwnProductImageService;
import dev.yukado.systemsmainz.service.own.OwnProductService;
import dev.yukado.systemsmainz.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/admin/own-product-images")
public class OwnProductImageAdminController {

    private final OwnProductService productService;
    private final OwnProductImageService imageService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public OwnProductImageAdminController(OwnProductService productService,
                                          OwnProductImageService imageService,
                                          UserDetailsService userDetailsService,
                                          UserService userService) {
        this.productService = productService;
        this.imageService = imageService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping
    public String list(@RequestParam Long productId, Model model, Principal principal) {

        // --- Admin Header Daten ---
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        String userName = "A" + id1;

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", id1);

        // --- Produktbilder ---
        OwnProduct product = productService.findById(productId);
        model.addAttribute("product", product);
        model.addAttribute("images", imageService.findByProduct(product));

        return "admin/own_product_images";
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("position") Integer position,
                         @RequestParam("productId") Long productId) throws IOException {

        OwnProduct product = productService.findById(productId);

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/" + filename);
        Files.write(path, file.getBytes());

        OwnProductImage img = new OwnProductImage();
        img.setProduct(product);
        img.setPosition(position);
        img.setUrl("/uploads/" + filename);

        imageService.save(img);

        return "redirect:/admin/own-product-images?productId=" + productId;
    }
}
