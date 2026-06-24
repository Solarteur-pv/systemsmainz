package dev.yukado.systemsmainz.config;

import dev.yukado.systemsmainz.entity.User;
import dev.yukado.systemsmainz.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class StartupRunner {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            String adminEmail = "info@solarteur.eu";

            if (!userRepository.existsByEmail(adminEmail)) {

                User admin = new User(
                        adminEmail,
                        passwordEncoder.encode("12345"),
                        "ADMIN"
                );

                userRepository.save(admin);
                System.out.println(">>> Admin-User wurde erstellt: " + adminEmail);
            } else {
                System.out.println(">>> Admin-User existiert bereits.");
            }
        };
    }
}

