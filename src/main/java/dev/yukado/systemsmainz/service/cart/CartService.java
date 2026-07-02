package dev.yukado.systemsmainz.service.cart;

import dev.yukado.systemsmainz.entity.Cart;

public interface CartService {

    Cart getCartForUser(Long userId);

    void addToCart(Long userId, Long productId, int quantity);

    void removeFromCart(Long userId, Long productId);

    int getItemCount(Long userId);

    void clearCart(Long userId);
}

