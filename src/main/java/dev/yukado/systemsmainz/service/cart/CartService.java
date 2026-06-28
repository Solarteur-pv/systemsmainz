package dev.yukado.systemsmainz.service.cart;

import dev.yukado.systemsmainz.entity.Cart;

public interface CartService {

    Cart getCartForSession(String sessionId);

    void addToCart(String sessionId, Long productId, int quantity);

    void removeFromCart(String sessionId, Long productId);

    int getItemCount(String sessionId);

    void clearCart(String sessionId);
}
