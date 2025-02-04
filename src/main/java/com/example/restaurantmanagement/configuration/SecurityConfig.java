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

                        // ----------------------------USER------------------------------
                        // Permit All
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/verify-email").permitAll()
                        .requestMatchers("/user/reset-password/get-token").permitAll()
                        // Every authenticated
                        .requestMatchers(HttpMethod.GET, "/user/{user-id}").authenticated()
                        // ADMIN & USER
                        .requestMatchers(HttpMethod.GET, "/user/customer").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers(HttpMethod.POST, "/user/register/moderator").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/user/{user-id}").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers(HttpMethod.DELETE, "/user/{user-id}").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/user/**").hasAuthority(Role.ADMIN.name())

                        // ----------------------------ORDER------------------------------
                        .requestMatchers(HttpMethod.GET, "/order/user").authenticated()
                        .requestMatchers(HttpMethod.POST, "/order").authenticated()
                        .requestMatchers(HttpMethod.GET, "/order/{order-id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/order/{order-id}").authenticated()
                        .requestMatchers("/order/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------MEAL------------------------------
                        .requestMatchers(HttpMethod.GET, "/meal").permitAll()
                        .requestMatchers("/meal/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------MEAL_CATEGORY------------------------------
                        .requestMatchers(HttpMethod.GET, "/meal-category/**").permitAll()
                        .requestMatchers("/meal-category").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())


                        // ----------------------------RESERVATION------------------------------
                        .requestMatchers("/reservation/user/{user-id}").authenticated()
                        .requestMatchers("/reservation/user").authenticated()
                        .requestMatchers(HttpMethod.GET, "/reservation").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers(HttpMethod.POST, "/reservation").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/reservation/{reservation-id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/reservation/{reservation-id}").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers("/reservation/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())


                        // ----------------------------Table------------------------------
                        .requestMatchers(HttpMethod.GET, "/table/**").permitAll()
                        .requestMatchers("/table/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------Feedback------------------------------
                        .requestMatchers(HttpMethod.GET, "/feedback/{feedback-id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/feedback/user").authenticated()
                        .requestMatchers(HttpMethod.GET, "/feedback/user/{user-id}").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/feedback").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers(HttpMethod.POST, "/feedback").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/feedback/{feedback-id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/feedback/{feedback-id}").authenticated()

                        // ----------------------------Banner------------------------------
                        .requestMatchers(HttpMethod.GET, "/banner").permitAll()
                        .requestMatchers("/banner/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------AboutUs------------------------------
                        .requestMatchers(HttpMethod.GET, "/about-us").permitAll()
                        .requestMatchers("/about-us/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------Address------------------------------
                        .requestMatchers(HttpMethod.GET, "/address").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers("/address/**").authenticated()


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