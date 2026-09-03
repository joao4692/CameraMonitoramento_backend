package com.example.cameramonitoramento.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getRequestURI().substring(request.getContextPath().length());

        // Se for rota pública, deixa passar sem validação
        if (requestPath.startsWith("api/auth") || requestPath.startsWith("api/public")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extrai o token do header Authorization
            String token = extractTokenFromRequest(request);

            // Se tem token e é válido
            if (token != null && jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsernameFromToken(token);

                if (username != null) {
                    // Cria um objeto de autenticação
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

                    // Armazena na segurança do Spring
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.debug("Usuário autenticado: {}", username);
                }
            }
        } catch (Exception e) {
            logger.error("Erro ao validar JWT: {}", e.getMessage());
        }

        // Continua para o próximo filtro
        filterChain.doFilter(request, response);
    }

    /**
     * Extrai o token do header Authorization
     * Formato esperado: "Authorization: Bearer <token>"
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }
}
