package dev.revington;

import dev.revington.entity.Token;
import dev.revington.entity.User;
import dev.revington.repository.TokenRepository;
import dev.revington.repository.UserRepository;
import dev.revington.util.CookieUtil;
import dev.revington.variables.Parameter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class WebConfig {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    private final Logger LOGGER = Logger.getLogger(WebConfig.class.getName());

    @Bean
    public SecurityFilterChain secure(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http
                .csrf().disable()
                .authorizeHttpRequests().requestMatchers("/api/**", "/error").permitAll();

        return http.build();
    }

    @Bean
    public FilterRegistrationBean<UserFilter> registerFilter() {
        FilterRegistrationBean<UserFilter> filters = new FilterRegistrationBean<>();
        filters.setFilter(new UserFilter());
        filters.addUrlPatterns("/api/v2/social/*", "/api/v2/account/*");
        return filters;
    }

    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> registerUserForbiddenFilter() {
        FilterRegistrationBean<OncePerRequestFilter> filters = new FilterRegistrationBean<>();
        filters.setFilter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String tokenValue = CookieUtil.getCookieValue(request, Parameter.TOKEN);
                if (tokenValue != null && !tokenValue.isBlank()) {
                    response.sendError(1021, Parameter.E1021);
                    return;
                }

                filterChain.doFilter(request, response);
            }
        });
        filters.addUrlPatterns("/api/v2/login", "/api/v2/signup");
        return filters;
    }

    private class UserFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String tokenValue = CookieUtil.getCookieValue(request, Parameter.TOKEN);
            if (tokenValue == null || tokenValue.isBlank()) {
                response.sendError(1021, Parameter.E1021);
                return;
            }

            Token token = tokenRepository.findByToken(tokenValue);
            tokenValue = null;
            if (token == null || token.getExpires() <= new Date().getTime()) {
                CookieUtil.setCookie(response, Parameter.TOKEN, cookie -> {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                });
                response.sendError(1021, Parameter.E1021);

                if (token != null)
                    tokenRepository.deleteById(token.getId());
                return;
            }

            Optional<User> userOpt = userRepository.findById(token.getClientId());
            if (userOpt.isEmpty()) {
                response.sendError(1021, Parameter.E1021);
            } else {
                request.setAttribute(Parameter.CLIENT_ID, token.getClientId());
                if (request.getRequestURI().contains("account"))
                    request.setAttribute(Parameter.TOKEN_ID, token.getId());
                filterChain.doFilter(request, response);
            }
        }

    }

}
