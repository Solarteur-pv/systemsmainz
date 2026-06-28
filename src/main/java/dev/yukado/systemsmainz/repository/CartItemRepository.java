package dev.yukado.systemsmainz.repository;

import dev.yukado.systemsmainz.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

