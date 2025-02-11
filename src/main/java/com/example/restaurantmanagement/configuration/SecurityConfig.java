package com.example.restaurantmanagement.configuration;


import com.example.restaurantmanagement.enums.Role;
import com.example.restaurantmanagement.util.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedOrigin("https://dashboard.frango.software");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply configuration to all paths
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((request) -> request
                        // ----------------------------SWAGGER------------------------------
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        // ----------------------------AUTHENTICATION------------------------------
                        .requestMatchers("/auth/**").permitAll()

                        // ----------------------------UPLOADS------------------------------
                        .requestMatchers("/uploads/**").permitAll()
                        // ----------------------------DEPLOY------------------------------
                        .requestMatchers("/deploy/**").permitAll()

                        // ----------------------------USER------------------------------
                        // Permit All
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/verify-email").permitAll()
                        .requestMatchers("/account/reset-password/**").permitAll()
                        // Every authenticated
                        .requestMatchers(HttpMethod.GET, "/users/{user-id}").authenticated()
                        // ADMIN & USER
                        .requestMatchers(HttpMethod.GET, "/users/customer").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers(HttpMethod.POST, "/users/register/moderator").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/users/{user-id}").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers(HttpMethod.DELETE, "/users/{user-id}").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/users/**").hasAuthority(Role.ADMIN.name())

                        // ----------------------------ORDER------------------------------
                        .requestMatchers(HttpMethod.GET, "/orders/user").authenticated()
                        .requestMatchers(HttpMethod.POST, "/orders").authenticated()
                        .requestMatchers(HttpMethod.GET, "/orders/{order-id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/orders/{order-id}").authenticated()
                        .requestMatchers("/orders/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------MEAL------------------------------
                        .requestMatchers(HttpMethod.GET, "/meals").permitAll()
                        .requestMatchers("/meals/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------MEAL_CATEGORY------------------------------
                        .requestMatchers(HttpMethod.GET, "/meal-categories/**").permitAll()
                        .requestMatchers("/meal-categories").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())


                        // ----------------------------RESERVATION------------------------------
                        .requestMatchers("/reservations/user/{user-id}").authenticated()
                        .requestMatchers("/reservations/user").authenticated()
                        .requestMatchers(HttpMethod.GET, "/reservations").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers(HttpMethod.POST, "/reservations").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/reservations/{reservation-id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/reservations/{reservation-id}").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers("/reservations/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())


                        // ----------------------------Table------------------------------
                        .requestMatchers(HttpMethod.GET, "/tables/**").permitAll()
                        .requestMatchers("/tables/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------Feedback------------------------------
                        .requestMatchers(HttpMethod.GET, "/feedbacks/{feedback-id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/feedbacks/user").authenticated()
                        .requestMatchers(HttpMethod.GET, "/feedbacks/user/{user-id}").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/feedbacks").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers(HttpMethod.POST, "/feedbacks").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/feedbacks/{feedback-id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/feedbacks/{feedback-id}").authenticated()

                        // ----------------------------Banner------------------------------
                        .requestMatchers(HttpMethod.GET, "/banners").permitAll()
                        .requestMatchers("/banners/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------AboutUs------------------------------
                        .requestMatchers(HttpMethod.GET, "/about-us").permitAll()
                        .requestMatchers("/about-us/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------Address------------------------------
                        .requestMatchers(HttpMethod.GET, "/addresses").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers("/addresses/**").authenticated()


                        .anyRequest().authenticated()

                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

                .logout((logout) -> {
                    logout.logoutUrl("/logout");
                    logout.permitAll();
                });

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/authenticate"
    };
}