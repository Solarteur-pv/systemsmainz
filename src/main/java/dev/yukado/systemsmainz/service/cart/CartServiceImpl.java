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

    public CartServiceImpl(CartRepository cartRepo,
                           CartItemRepository itemRepo,
                           ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.itemRepo = itemRepo;
        this.productRepo = productRepo;
    }

    @Override
    public Cart getCartForUser(Long userId) {
        return cartRepo.findById(userId)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setId(userId);

                    return cartRepo.save(c);
                });
    }

    @Override
    public void addToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartForUser(userId);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produkt nicht gefunden"));

        Optional<CartItem> existing = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setCart(cart);
            cart.getItems().add(item);
        }

        cartRepo.save(cart);
    }

    @Override
    public void removeFromCart(Long userId, Long productId) {
        Cart cart = getCartForUser(userId);
        cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));
        cartRepo.save(cart);
    }

    @Override
    public int getItemCount(Long userId) {
        return getCartForUser(userId).getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getCartForUser(userId);
        cart.getItems().clear();
        cartRepo.save(cart);
    }
}
