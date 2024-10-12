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
        configuration.addAllowedOrigin("http://localhost:5173"); // Allow your frontend origin
        configuration.addAllowedMethod("*"); // Allow all methods (GET, POST, etc.)
        configuration.addAllowedHeader("*"); // Allow all headers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS configuration to all endpoints
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((request) -> request
                        // ----------------------------AUTHENTICATION------------------------------
                        .requestMatchers("/auth/**").permitAll()

                        // ----------------------------USER------------------------------
                        // Permit All
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/verifyEmail").permitAll()
                        .requestMatchers("/user/resetPassword/getToken").permitAll()
                        // Every authenticated
                        .requestMatchers(HttpMethod.GET, "/user/{userId}").authenticated()
                        // ADMIN & USER
                        .requestMatchers(HttpMethod.GET, "/user/customer").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers(HttpMethod.POST, "/user/register/moderator").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/user/{userId}").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers(HttpMethod.DELETE, "/user/{userId}").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/user/**").hasAuthority(Role.ADMIN.name())

                        // ----------------------------ORDER------------------------------
                        .requestMatchers(HttpMethod.GET, "/order/user").authenticated()
                        .requestMatchers(HttpMethod.POST, "/order").authenticated()
                        .requestMatchers(HttpMethod.GET, "/order/{orderId}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/order/{orderId}").authenticated()
                        .requestMatchers("/order/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------MEAL------------------------------
                        .requestMatchers(HttpMethod.GET, "/meal").permitAll()
                        .requestMatchers("/meal/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------MEAL_CATEGORY------------------------------
                        .requestMatchers(HttpMethod.GET, "/mealCategory/**").permitAll()
                        .requestMatchers("/mealCategory").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())


                        // ----------------------------RESERVATION------------------------------
                        .requestMatchers("/reservation/user/{userId}").authenticated()
                        .requestMatchers("/reservation/user").authenticated()
                        .requestMatchers(HttpMethod.GET, "/reservation").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers(HttpMethod.POST, "/reservation").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/reservation/{reservationId}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/reservation/{reservationId}").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers("/reservation/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())


                        // ----------------------------Table------------------------------
                        .requestMatchers(HttpMethod.GET, "/table/**").permitAll()
                        .requestMatchers("/table/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------Feedback------------------------------
                        .requestMatchers(HttpMethod.GET, "/feedback/{feedbackId}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/feedback/user").authenticated()
                        .requestMatchers(HttpMethod.GET, "/feedback/user/{userId}").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/feedback").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())
                        .requestMatchers(HttpMethod.POST, "/feedback").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/feedback/{feedbackId}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/feedback/{feedbackId}").authenticated()

                        // ----------------------------Banner------------------------------
                        .requestMatchers(HttpMethod.GET, "/banner").permitAll()
                        .requestMatchers("/banner/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

                        // ----------------------------AboutUs------------------------------
                        .requestMatchers(HttpMethod.GET, "/aboutUs").permitAll()
                        .requestMatchers("/aboutUs/**").hasAnyAuthority(Role.ADMIN.name(), Role.MODERATOR.name())

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

//        http.userDetailsService(userDetailsService);
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

}