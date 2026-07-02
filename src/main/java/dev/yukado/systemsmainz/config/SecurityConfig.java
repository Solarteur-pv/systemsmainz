package dev.yukado.systemsmainz.config;

import dev.yukado.systemsmainz.audit.AuditLogService;
import dev.yukado.systemsmainz.service.user.CustomFailureHandler;
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

    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailureHandler customFailureHandler;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomSuccessHandler customSuccessHandler,
                          CustomFailureHandler customFailureHandler,
                          CustomUserDetailsService customUserDetailsService) {
        this.customSuccessHandler = customSuccessHandler;
        this.customFailureHandler = customFailureHandler;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/", "/about", "/index",
                                "/favicon.ico", "/shop", "/referenzen", "/login", "/logout",
                                "/banner/image/**", "/homecard/image/**", "/error", "/fargments/header/**", "/shop/**",
                                "/cart/**", "/partner/**", "/datenschutz/**", "/impressum/**")
                        .permitAll()
                        .requestMatchers("/admin/dashboard").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/banner/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/products/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/product_create").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/viewlogs/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/users/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/bannerupload/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/homecardupload/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/homecard_edit/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/editbanner/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/banner_edit/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/edituser/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/registration_a/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/product_new").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/own_category/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/own_product/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/own_product_images/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/fragments/header-admin/**").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customSuccessHandler)
                        .failureHandler(customFailureHandler)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}

