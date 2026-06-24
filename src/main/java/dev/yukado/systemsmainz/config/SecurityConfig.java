package dev.yukado.systemsmainz.config;

import dev.yukado.systemsmainz.service.user.CustomSuccessHandler;
import dev.yukado.systemsmainz.service.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CSRF deaktivieren (je nach Bedarf anpassen)
                .csrf(AbstractHttpConfigurer::disable)

                // Zugriffsregeln definieren:
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/", "/about", "/index", "/favicon.ico",
                                "/products", "/referenzen", "/login", "/logout")
                        .permitAll()

                        // Admin
                        .requestMatchers("/admin/dashboard").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/banner").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/editbanner").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/banner_edit").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/bannerupload").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/homecard").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/edithomecard").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/homecardupload").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/homecard_edit").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                )

                // Login
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customSuccessHandler)
                        .permitAll()
                )

                // Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
