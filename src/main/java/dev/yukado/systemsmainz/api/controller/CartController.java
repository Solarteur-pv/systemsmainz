package dev.yukado.systemsmainz.api.controller;

import dev.yukado.systemsmainz.entity.Cart;
import dev.yukado.systemsmainz.service.cart.CartService;
import dev.yukado.systemsmainz.service.user.CustomUserDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // CART PAGE (nur angemeldet)
    @GetMapping
    public String getCartPage(@AuthenticationPrincipal CustomUserDetail user,
                              Model model) {

        Long userId = user.getId();
        if (userId == null) {
            return "redirect:/login";
        }
        Cart cart = cartService.getCartForUser(userId);

        model.addAttribute("cart", cart);
        return "cart";
    }

    // ADD TO CART
    @PostMapping("/add")
    public String addToCart(@AuthenticationPrincipal CustomUserDetail user,
                            @RequestParam Long productId) {

        Long userId = user.getId();
        if (userId == null) {
            return "redirect:/login";
        }
        cartService.addToCart(userId, productId, 1);

        return "redirect:/cart";
    }

    // REMOVE FROM CART
    @PostMapping("/remove")
    public String removeFromCart(@AuthenticationPrincipal CustomUserDetail user,
                                 @RequestParam Long productId) {

        Long userId = user.getId();
        if (userId == null) {
            return "redirect:/login";
        }
        cartService.removeFromCart(userId, productId);
        return "redirect:/cart";
    }

    // CLEAR CART
    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal CustomUserDetail user) {

        Long userId = user.getId();
        if (userId == null) {
            return "redirect:/login";
        }
        cartService.clearCart(userId);
        return "redirect:/cart";
    }
}


