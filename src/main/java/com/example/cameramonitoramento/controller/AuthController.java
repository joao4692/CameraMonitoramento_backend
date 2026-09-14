package com.example.cameramonitoramento.controller;

import com.example.cameramonitoramento.security.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtProvider jwtProvider;

    @Value("${app.auth.admin-username}")
    private String adminUsername;

    @Value("${app.auth.admin-password}")
    private String adminPassword;

    /**
     * Endpoint de login
     * POST /api/auth/login
     * Body: { "username": "admin", "password": "admin123" }
     * Response: { "token": "eyJhbGc..." }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Validação simples (em produção, seria validado contra banco)
            if (adminUsername.equals(loginRequest.getUsername()) && adminPassword.equals(loginRequest.getPassword())) {

                // Gera o token JWT
                String token = jwtProvider.generateToken(loginRequest.getUsername());

                // Retorna o token
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("username", loginRequest.getUsername());
                response.put("message", "Login realizado com sucesso");

                logger.info("Login bem-sucedido para usuário: {}", loginRequest.getUsername());
                return ResponseEntity.ok(response);

            } else {
                // Credenciais inválidas
                Map<String, String> error = new HashMap<>();
                error.put("error", "Credenciais inválidas");

                logger.warn("Tentativa de login com credenciais inválidas");
                return ResponseEntity.status(401).body(error);
            }
        } catch (Exception e) {
            logger.error("Erro ao fazer login", e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erro ao processar login");
            return ResponseEntity.status(500).body(error);
        }
    }

    /**
     * Classe interna para receber dados do login
     */
   
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters e setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
