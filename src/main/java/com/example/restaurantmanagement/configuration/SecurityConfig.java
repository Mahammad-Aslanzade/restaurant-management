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
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
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
                .cors().and()

                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/user").hasAuthority(Role.ADMIN.name())
                                .requestMatchers("/uploads/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/aboutUs").permitAll()
                                .requestMatchers(HttpMethod.GET, "/meal/**").permitAll()
                                .requestMatchers(HttpMethod.GET , "/mealCategory/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/table/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/feedback/**").permitAll()
                                .requestMatchers("/user/register/ordinary").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
//                        .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER")
                                .anyRequest().authenticated()
                )
//                .exceptionHandling((exception)-> exception.accessDeniedHandler(accessDeniedHandler))

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .csrf(AbstractHttpConfigurer::disable)
                .logout(LogoutConfigurer::permitAll);
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