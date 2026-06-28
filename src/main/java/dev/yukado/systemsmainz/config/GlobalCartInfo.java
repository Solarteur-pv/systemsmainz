package dev.yukado.systemsmainz.config;

import dev.yukado.systemsmainz.service.cart.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalCartInfo {

    @Autowired
    private CartService cartService;

    @ModelAttribute("cartCount")
    public int cartCount(HttpSession session) {
        return cartService.getItemCount(session.getId());
    }
}




