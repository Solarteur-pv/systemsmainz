package dev.yukado.systemsmainz.config;

import dev.yukado.systemsmainz.service.cart.CartService;
import dev.yukado.systemsmainz.service.user.CustomUserDetail;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalCartInfo {

    private final CartService cartService;

    public GlobalCartInfo(CartService cartService) {
        this.cartService = cartService;
    }

    @ModelAttribute("cartCount")
    public int cartCount(@AuthenticationPrincipal CustomUserDetail user) {

        if (user == null) {
            return 0; // nicht eingeloggt → kein Warenkorb
        }

        Long userId = user.getId();
        return cartService.getItemCount(userId);
    }
}






