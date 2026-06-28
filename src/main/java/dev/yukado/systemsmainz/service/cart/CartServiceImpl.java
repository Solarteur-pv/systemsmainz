package dev.yukado.systemsmainz.service.cart;

import dev.yukado.systemsmainz.entity.Cart;
import dev.yukado.systemsmainz.entity.CartItem;
import dev.yukado.systemsmainz.entity.Product;
import dev.yukado.systemsmainz.entity.ProductPrice;
import dev.yukado.systemsmainz.repository.CartItemRepository;
import dev.yukado.systemsmainz.repository.CartRepository;
import dev.yukado.systemsmainz.repository.ProductPriceRepository;
import dev.yukado.systemsmainz.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository itemRepo;
    private final ProductRepository productRepo;
    private final ProductPriceRepository priceRepo;

    public CartServiceImpl(CartRepository cartRepo,
                           CartItemRepository itemRepo,
                           ProductRepository productRepo,
                           ProductPriceRepository priceRepo) {
        this.cartRepo = cartRepo;
        this.itemRepo = itemRepo;
        this.productRepo = productRepo;
        this.priceRepo = priceRepo;
    }

    @Override
    public Cart getCartForSession(String sessionId) {
        return cartRepo.findBySessionId(sessionId)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setSessionId(sessionId);
                    return cartRepo.save(c);
                });
    }

    @Override
    public void addToCart(String sessionId, Long productId, int quantity) {

        Cart cart = getCartForSession(sessionId);

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produkt nicht gefunden"));

        ProductPrice price = priceRepo.findByProduct(product)
                .orElseThrow(() -> new RuntimeException("Preis nicht gefunden"));

        Optional<CartItem> existing = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPriceNet(price.getPriceNet().doubleValue()); // Netto
            item.setVatRate(price.getVatRate().doubleValue());   // z.B. 0.19
            item.setCart(cart);
            cart.getItems().add(item);
        }

        cartRepo.save(cart);
    }

    @Override
    public void removeFromCart(String sessionId, Long productId) {
        Cart cart = getCartForSession(sessionId);
        cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));
        cartRepo.save(cart);
    }

    @Override
    public int getItemCount(String sessionId) {
        return getCartForSession(sessionId).getTotalQuantity();
    }

    @Override
    public void clearCart(String sessionId) {
        Cart cart = getCartForSession(sessionId);
        cart.getItems().clear();
        cartRepo.save(cart);
    }
}
