package dev.yukado.systemsmainz.repository;

import dev.yukado.systemsmainz.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    // Methode um Benutzernamen (oder hier E-Mail) case-insensitiv zu suchen:
    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.email = :email, u.password = :password, u.role = :role WHERE u.id = :id")
    void updateUserById(Long id, String email, String password, String role);

    Optional <User> findUserByEmail(String email);

    boolean existsByEmail(String email);
}
