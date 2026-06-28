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
                                "/favicon.ico", "/products", "/referenzen", "/login", "/logout", "/banner/image/**", "/homecard/image/**")
                        .permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/bannerupload/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/homecardupload/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/homecard_edit/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/banner_edit/**").hasAnyAuthority("ADMIN")
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

