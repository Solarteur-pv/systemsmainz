package dev.yukado.systemsmainz.repository;

import dev.yukado.systemsmainz.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findBySessionId(String sessionId);
}


