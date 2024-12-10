package com.nqbao.project.configuration.filter;
import com.nqbao.project.service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    private final UserDetailsService userService;

    //For postman test to get jwt
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String prefix = "Bearer ";
        Optional.of("Authorization")
                .map(request::getHeader)
                .filter(header -> header.startsWith(prefix))
                .map(header -> header.replaceFirst(prefix, ""))
                .map(tokenService::extractToken)
                .map(Claims::getSubject)
                .map(userService::loadUserByUsername)
                .map(user -> {
                    var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    return authToken;
                }).ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        filterChain.doFilter(request, response);
    }

//for browse to get jwt
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("JWT_TOKEN".equals(cookie.getName())) {
//                    String token = cookie.getValue();
//                    String username = tokenService.extractToken(token).getSubject();
//
//                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                        UserDetails userDetails = userService.loadUserByUsername(username);
//                        UsernamePasswordAuthenticationToken authToken =
//                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//                    }
//                    break;
//                }
//            }
//        }
//        filterChain.doFilter(request, response);
//    }

}




