package dev.yukado.systemsmainz.service.user;

import dev.yukado.systemsmainz.dto.UserDto;
import dev.yukado.systemsmainz.entity.User;
import dev.yukado.systemsmainz.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registrierung
    @Override
    public User save(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        return userRepository.save(user);
    }

    // Update über User-Objekt
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // WICHTIG: Pagination + Suche funktionieren hier
    @Override
    public Page<User> findPaginatedUsers(String search, Pageable pageable) {

        // Wenn keine Suche → alle User paginiert zurückgeben
        if (search == null || search.isBlank()) {
            return userRepository.findAll(pageable);
        }

        // Wenn Suche aktiv → gefilterte User zurückgeben
        return userRepository.findByEmailContainingIgnoreCase(search, pageable);
    }

    @Override
    public Page<User> findPaginatedUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> searchUsers(String search, Pageable pageable) {
        return userRepository.findByEmailContainingIgnoreCase(search, pageable);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findOptionalByEmail(email);
    }

    @Override
    public boolean updateUser(User user) {
        Optional<User> existing = userRepository.findById(user.getId());
        if (existing.isEmpty()) return false;

        User u = existing.get();
        u.setEmail(user.getEmail());
        u.setRole(user.getRole());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(u);
        return true;
    }

    @Override
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("User nicht gefunden"));

        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());

        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        userRepository.save(user);
    }
}
