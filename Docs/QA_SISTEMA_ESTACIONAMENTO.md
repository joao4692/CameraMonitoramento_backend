# 📚 Perguntas & Respostas — Sistema de Estacionamento Inteligente

**Criado por:** JU (Joana Psicólogo)  
**Data:** 02/09/2026  
**Status:** ✅ Etapas 1-4 Completas

---

## 🎯 Seção 1: Conceitos Básicos

### P1: O que é uma API REST?
**R:** API REST é uma forma padronizada de criar serviços web que usa URLs (rotas) e HTTP para comunicação.

**Exemplo:**
- `GET /api/estacionamentos/1` → busca estacionamento com id 1
- `POST /api/auth/login` → faz login
- `PUT /api/estacionamentos/1` → atualiza estacionamento 1
- `DELETE /api/estacionamentos/1` → deleta estacionamento 1

No nosso projeto, Spring Boot cria essas rotas automaticamente a partir dos controllers.

---

### P2: O que é JWT (JSON Web Token)?
**R:** JWT é um padrão de segurança que funciona assim:

1. **Usuário faz login** → servidor gera um token (uma string longa)
2. **Token é armazenado no cliente** (Angular)
3. **Cliente envia o token a cada requisição** no header: `Authorization: Bearer <token>`
4. **Servidor valida o token** → se válido, processa a requisição

**Vantagem:** Servidor não precisa armazenar sessão (stateless).

---

### P3: JWT tem 3 partes. Qual é cada uma?
**R:** Separadas por `.`:

1. **Header** (eyJhbGc...): diz qual algoritmo foi usado (HS512)
2. **Payload** (eyJzdWI...): dados do usuário (quem é, quando expira)
3. **Signature** (9RH944...): assinatura que prova que é legítimo

**Exemplo:**
```
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc4ODM3MzI0OSwiZXhwIjoxNzg4NDU5NjQ5fQ.9RH944lh...
```

**Por que importa:** A assinatura garante que o token não foi modificado. Se alguém tentar mudar o payload, a assinatura não vai bater.

---

## 🔐 Seção 2: Spring Security & Autenticação

### P4: O que faz o `@EnableWebSecurity`?
**R:** Ativa o Spring Security no projeto. Sem isso, a segurança não funciona.

Spring Security automaticamente:
- Bloqueia TODAS as rotas por padrão
- Cria filtros para validar cada requisição
- Protege contra CSRF, XSS, etc.

---

### P5: O que significa "stateless" em Spring Security?
**R:** Stateless = servidor NÃO armazena informação sobre o usuário.

**Com sessão (stateful):**
```
Cliente → Login → Servidor cria Sessão (banco de dados)
Cliente → Requisição + ID_SESSAO → Servidor busca sessão no banco
```

**Com JWT (stateless):**
```
Cliente → Login → Servidor gera Token JWT
Cliente → Requisição + Token → Servidor valida Token (sem banco)
```

No nosso projeto, usamos JWT, então o servidor é stateless.

---

### P6: O que faz `.authorizeHttpRequests()`?
**R:** Define quais rotas precisam de autenticação e quais são públicas.

**No nosso código:**
```java
.authorizeHttpRequests(authz -> authz
    .requestMatchers("/api/auth/**").permitAll()      // login é público
    .requestMatchers("/api/public/**").permitAll()    // rotas públicas
    .anyRequest().authenticated()                      // tudo o mais precisa token
)
```

**Tradução:**
- `/api/auth/login` → qualquer um pode acessar (sem token)
- `/api/public/*` → qualquer um pode acessar (sem token)
- Todas as outras rotas → precisa ter token válido

---

### P7: O que faz `csrf().disable()`?
**R:** Desabilita proteção CSRF (Cross-Site Request Forgery).

**Por quê desabilitar?** JWT não precisa porque:
- CSRF é vulnerabilidade de sessão (cookies automáticos)
- JWT precisa ser enviado manualmente no header (mais seguro)

---

## 🔑 Seção 3: JwtProvider

### P8: O que faz o método `generateToken()`?
**R:** Cria um novo token JWT para um usuário.

**O que acontece:**
1. Cria um objeto com dados: username, data criação, data expiração
2. Assina com a chave secreta (HS512)
3. Retorna a string do token

**Exemplo:**
```java
String token = jwtProvider.generateToken("admin");
// Retorna: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIs...
```

---

### P9: O que faz o método `validateToken()`?
**R:** Valida se um token é legítimo.

**Verifica:**
1. Se o token foi modificado (valida assinatura)
2. Se não expirou (compara data de expiração com agora)
3. Se está bem formatado

**Retorna:**
- `true` → token válido
- `false` → token inválido/expirado

---

### P10: Por que precisamos de uma chave secreta de 64 bytes?
**R:** HS512 é um algoritmo de criptografia que exige uma chave mínima de 512 bits (64 bytes).

**Se a chave for curta:**
```
java.lang.IllegalArgumentException: The key provided is shorter than the minimum required size...
```

**Solução:** Gerar chave Base64 com 64+ bytes:
```bash
openssl rand -base64 64
```

---

## 🔍 Seção 4: Filtros & Controllers

### P11: O que faz o `JwtAuthenticationFilter`?
**R:** É um filtro que roda a CADA requisição HTTP.

**Fluxo:**
1. Requisição chega
2. JwtAuthenticationFilter intercepta
3. Extrai token do header `Authorization: Bearer <token>`
4. Valida com JwtProvider
5. Se válido: armazena quem é o usuário em `SecurityContextHolder`
6. Se inválido: deixa passar (Spring Security bloqueia depois)

**Por que filtro?** Porque precisa rodar ANTES do controller para validar.

---

### P12: O que faz o `extractTokenFromRequest()`?
**R:** Remove o prefixo "Bearer " do header Authorization.

**Exemplo:**
```
Header: Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWI...
Retorna: eyJhbGciOiJIUzUxMiJ9.eyJzdWI...
```

Se não remover, JwtProvider tenta validar "Bearer eyJhbGc..." inteiro (errado).

---

### P13: O que faz o `AuthController`?
**R:** Tem o endpoint de login que retorna o token.

**Fluxo:**
```
Cliente POST /api/auth/login
    ↓
AuthController recebe username/password
    ↓
Valida credenciais (admin/admin123)
    ↓
Se válido: JwtProvider.generateToken("admin")
    ↓
Retorna JSON com token
```

---

### P14: Por que tem `@CrossOrigin` no AuthController?
**R:** Permite que Angular (rodando na porta 4200) faça requisições para Java (porta 8080).

Sem isso, navegador bloqueia (erro CORS).

```java
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
```

Isso diz: "aceita requisições dessas origens".

---

## 📊 Seção 5: Fluxo Completo

### P15: Qual é o fluxo completo de autenticação?
**R:**

**1. Login:**
```
curl POST /api/auth/login
Body: {"username":"admin", "password":"admin123"}
↓
AuthController valida
↓
JwtProvider.generateToken("admin")
↓
Response: {"token": "eyJhbGc..."}
```

**2. Requisição Autenticada:**
```
curl GET /api/estacionamentos
Header: Authorization: Bearer eyJhbGc...
↓
JwtAuthenticationFilter extrai token
↓
JwtProvider.validateToken(token) → true
↓
SecurityContextHolder armazena username
↓
Spring permite acesso ao controller
↓
Controller processa normalmente
```

**3. Se Token Inválido:**
```
curl GET /api/estacionamentos
Header: Authorization: Bearer INVALIDO
↓
JwtAuthenticationFilter extrai token
↓
JwtProvider.validateToken(token) → false
↓
SecurityContextHolder fica vazio
↓
Spring bloqueia com erro 401 (Unauthorized)
```

---

### P16: O que significa HTTP 401 vs 403?
**R:**

| Código | Significado | Exemplo |
|--------|-------------|---------|
| **401** | Unauthorized (falta autenticação) | Token expirado ou inválido |
| **403** | Forbidden (autenticado mas sem permissão) | Token válido mas usuário não é admin |

No nosso projeto até agora, todo token válido tem acesso (não temos permissões ainda).

---

## 🛠️ Seção 6: application.properties

### P17: O que são as propriedades `app.jwt.*`?
**R:** Configurações que o JwtProvider lê com `@Value`.

```properties
app.jwt.secret=bXlfc3VwZXJfc2VjcmV0X2tleV9mb3JfaHM1MTJfaXNfdmVyeV9sb25nX2FuZF92ZXJ5X3NlY3VyZQ==
app.jwt.expiration=86400000
```

- `app.jwt.secret` → chave para assinar tokens (NUNCA exponha!)
- `app.jwt.expiration` → quanto tempo token dura em ms (86400000 = 24 horas)

---

### P18: Por que não pode compartilhar a chave secreta?
**R:** Se alguém tiver a chave, pode:
1. Validar tokens (ler dados)
2. **Criar tokens falsos** (hackers podem fingir ser admin)

**Analogia:** É como a senha do seu banco — se vazar, qualquer um acessa sua conta.

---

## 📁 Seção 7: Estrutura de Pastas

### P19: Por que dividimos o código em pastas (model, controller, service, etc.)?
**R:** Separação de responsabilidades. Cada camada tem um job:

| Pasta | Responsabilidade | Exemplo |
|-------|------------------|---------|
| **model/** | Definem as tabelas do banco | `Estacionamento.java` |
| **repository/** | Acesso ao banco | `EstacionamentoRepository.java` |
| **service/** | Lógica de negócio | `EstacionamentoService.java` (criamos depois) |
| **controller/** | Rotas HTTP | `AuthController.java` |
| **security/** | Autenticação/Autorização | `JwtProvider.java`, `JwtAuthenticationFilter.java` |
| **config/** | Configurações | `SecurityConfig.java` |

**Benefício:** Código organizado, fácil manutenção, reutilizável.

---

### P20: O que significa "Injeção de Dependência" (DI)?
**R:** Spring automaticamente cria objetos e os "injeta" onde você precisa.

**Exemplo:**
```java
@Autowired
private JwtProvider jwtProvider;
```

Spring vê `@Autowired` e:
1. Procura por `@Component` ou `@Bean` de `JwtProvider`
2. Cria uma instância
3. Armazena em `jwtProvider`

**Sem DI (errado):**
```java
JwtProvider jwtProvider = new JwtProvider(); // você mesmo cria
```

**Com DI (certo):**
```java
@Autowired
private JwtProvider jwtProvider; // Spring cria
```

---

## 🎯 Seção 8: Próximos Passos

### P21: O que fazer depois da autenticação?
**R:** Criar endpoints protegidos que usam o token.

**Exemplo — Próxima Etapa 5:**
- `GET /api/estacionamentos/{id}` → retorna vagas disponíveis
- `POST /api/eventos` → registra entrada/saída

Estes endpoints vão:
1. Receber token no header
2. JwtAuthenticationFilter valida
3. Controller processa

---

### P22: Como o Python vai usar o sistema?
**R:** Python vai enviar eventos via WebSocket/RabbitMQ:

```python
# Python detecta carro
evento = {
    "tipo": "entrada",
    "estacionamentoId": 1,
    "timestamp": "2026-09-02T15:00:00"
}

# Envia para RabbitMQ
broker.send(evento)
```

Spring escuta a fila e processa.

---

### P23: Como Angular vai usar o sistema?
**R:** Angular vai:

1. **Login:**
   ```javascript
   POST /api/auth/login
   Body: {username, password}
   → Recebe token
   → Armazena em localStorage
   ```

2. **Requisição com Token:**
   ```javascript
   GET /api/estacionamentos/1
   Header: Authorization: Bearer ${token}
   → Recebe dados
   ```

3. **WebSocket em Tempo Real:**
   ```javascript
   ws.connect("ws://localhost:8080/api/ws/estacionamento")
   → Recebe atualizações em tempo real
   ```

---

## 🔄 Seção 9: Conceitos Importantes

### P24: O que é um "Padrão MVC"?
**R:** Model-View-Controller. Separação em 3 camadas:

| Camada | O quê | No nosso projeto |
|--------|-------|------------------|
| **Model** | Dados (tabelas) | `Estacionamento.java` |
| **View** | Interface | Angular (não é Java) |
| **Controller** | Lógica HTTP | `AuthController.java` |

**Fluxo:**
```
HTTP Request → Controller → Service → Repository → Model (BD)
                ↓
            Retorna Response
```

---

### P25: O que é "Bean" no Spring?
**R:** Objeto que Spring gerencia automaticamente.

**Criado com:**
```java
@Component    // ou
@Service      // ou
@Repository   // ou
@Bean
public class MinhaClasse { }
```

**Spring faz:**
1. Cria instância
2. Injeta dependências
3. Gerencia ciclo de vida

---

## 📈 Progresso do Projeto

```
Etapa 1: [████████████████████] ✅ COMPLETA (Git)
Etapa 2: [████████████████████] ✅ COMPLETA (Spring Base)
Etapa 3: [████████████████████] ✅ COMPLETA (Banco)
Etapa 4: [████████████████████] ✅ COMPLETA (Autenticação)
─────────────────────────────────────────────────
Próximas:
Etapa 5: [░░░░░░░░░░░░░░░░░░░░] 0% (Endpoints Públicos)
Etapa 6: [░░░░░░░░░░░░░░░░░░░░] 0% (RabbitMQ)
Etapa 7: [░░░░░░░░░░░░░░░░░░░░] 0% (WebSocket)
Etapa 8: [░░░░░░░░░░░░░░░░░░░░] 0% (Polimento)
```

---

## 🎓 Resumo do Aprendizado

✅ Git e estrutura de projeto  
✅ Spring Boot e Maven  
✅ PostgreSQL e JPA  
✅ Autenticação com JWT  
✅ Spring Security  
✅ Controllers e Filtros  
✅ Injeção de Dependência  

---

**Próximo:** Etapa 5 — Criar endpoints públicos de estacionamento

