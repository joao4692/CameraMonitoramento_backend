# 🔵 Backend Java - Spring Boot | Plano de Desenvolvimento

**Responsável:** Joao Carlos (Desenvolvedor JAVA)  
**Repositório:** https://github.com/joao4692/CameraMonitoramento_backend  
**Stack:** Java 17 + Spring Boot 3.3.0 + PostgreSQL 16 + Maven  
**Data de Início:** 02/09/2026  
**Status Geral:** ⏳ Etapa 5 Completa / Etapa 6 Iniciando

---

## 📌 Visão Geral

Backend responsável por:

- ✅ Autenticação e autorização (JWT + Spring Security)
- ✅ Gerenciamento de estacionamentos e vagas
- ⏳ Receber eventos de entrada/saída (Python Service)
- ⏳ Broadcast de atualizações em tempo real (WebSocket → Angular)
- ⏳ Processar eventos e atualizar banco de dados

**Este backend integra com:**

- 🟠 **Frontend Angular** (em outro repo) → REST API + WebSocket
- 🟢 **Service Python** (em outro repo) → Eventos de detecção de veículos

---

## 🏗️ Arquitetura Backend

```
┌─────────────────────────────────────────────────────────────┐
│                  CLIENTE (Angular)                          │
│              REST API + WebSocket (ws://)                   │
└──────────────────────┬──────────────────────────────────────┘
                       │
        ┌──────────────▼──────────────┐
        │   BACKEND JAVA              │
        │   (Spring Boot + Security)  │
        │                             │
        │  • REST Endpoints           │
        │  • WebSocket Handler        │
        │  • Autenticação JWT         │
        │  • Lógica de Negócio        │
        └──────────┬──────────────────┘
                   │
        ┌──────────┴──────────────┐
        │                         │
    ┌───▼────┐             ┌──────▼──┐
    │ PostgreSQL      │  Python Service │
    │ (Banco Dados)   │  (OpenCV)       │
    │                 │  (Eventos)      │
    └─────────────────┴─────────────────┘
```

**Fluxo de Dados:**

1. Python detecta veículo → envia evento JSON para Backend
2. Backend processa → atualiza banco de dados
3. Backend broadcast para Angular via WebSocket
4. Angular mostra mudanças em tempo real

---

## 📋 Etapas de Desenvolvimento Backend

### ✅ **Etapa 1: Setup e Estrutura Inicial**

**Status:** ✅ COMPLETA

- ✅ Projeto Spring Boot criado (Maven)
- ✅ Dependências adicionadas (Spring Web, JPA, PostgreSQL, JWT, Security)
- ✅ Estrutura de pastas (config, controller, service, model, repository, security)
- ✅ `.gitignore` e `README.md` criados
- ✅ Primeiro commit no Git
- ✅ Repositório remoto no GitHub

**Data:** 02/09/2026

---

### ✅ **Etapa 2: Configuração e Base do Projeto**

**Status:** ✅ COMPLETA

- ✅ `application.properties` configurado (PostgreSQL, porta 8080, context-path `/api`)
- ✅ `pom.xml` com todas as dependências
- ✅ Classe Application.java pronta
- ✅ Servidor rodando em `http://localhost:8080/api`

**Data:** 02/09/2026

---

### ✅ **Etapa 3: Banco de Dados e Models**

**Status:** ✅ COMPLETA

**Modelos criados:**

- `Estacionamento.java` → Tabela `estacionamentos`
    - id, nome, localizacao, totalVagas, vagasOcupadas
- `Eventos.java` → Tabela `eventos`
    - id, tipoEvento, dataHora, descricao, estacionamento (FK)

**Repositories:**

- `EstacionamentoRepository` → JpaRepository
- `EventoRepository` → JpaRepository

**Data:** 02/09/2026

---

### ✅ **Etapa 4: Autenticação JWT**

**Status:** ✅ COMPLETA

**Componentes implementados:**

- `JwtProvider.java` → Geração e validação de tokens (HS512)
- `JwtAuthenticationFilter.java` → Interceptor de requisições
- `SecurityConfig.java` → Configuração Spring Security
- `AuthController.java` → Endpoint POST `/api/auth/login`

**Endpoints:**

- `POST /api/auth/login` → Retorna JWT token
    - Credenciais: `admin` / `admin123` (hardcoded, para teste)

**Segurança:**

- ✅ Rotas públicas: `/api/auth/**`, `/api/public/**`
- ✅ Rotas protegidas: Precisam de header `Authorization: Bearer <token>`
- ✅ Sessão stateless (JWT)

**Data:** 02/09/2026

---

### ✅ **Etapa 5: Regras de Negócio e Endpoints Públicos**

**Status:** ✅ COMPLETA

**Services implementados:**

- `EstacionamentoService.java`
    - `buscarPorId(Long id)` → Encontra estacionamento
    - `podeEnINtrar(Estacionamento)` → Calcula vagas livres
    - `podeEntrar(Estacionamento)` → Valida se tem vaga
    - `registrarEntradaVeiculo(Long id)` → Incrementa ocupação
    - `registrarSaidaVeiculo(Long id)` → Decrementa ocupação

- `EventoService.java`
    - `registrarEvento(String tipo, Long estacionamentoId)` → Processa evento
    - Valida tipo (`entrada` ou `saida`)
    - Atualiza vagas do estacionamento

**Controllers implementados:**

- `EstacionamentoController.java`
    - `GET /api/public/estacionamentos/{id}` → Retorna status (público)
    - `POST /api/eventos` → Registra evento (autenticado)

**DTO criado:**

- `EventoRequest.java` → `{tipo, estacionamentoId}`

**Testes realizados:**

- ✅ Login: Retorna token JWT
- ✅ GET público: Retorna estacionamento com vagas livres
- ✅ POST evento: Registra entrada/saída e atualiza banco

**Data:** 03/09/2026

---

### ⏳ **Etapa 6: WebSocket (Atualizações em Tempo Real)**

**Status:** 🔴 NÃO INICIADA

**O que fazer:**

1. Configurar Spring WebSocket
2. Criar `WebSocketConfig.java`
3. Criar `EstacionamentoWebSocketHandler.java`
4. Endpoint WebSocket: `ws://localhost:8080/api/ws/estacionamento`
5. Ao receber evento (Etapa 7), fazer broadcast para clientes conectados
6. Payload: `{id, nome, vagasOcupadas, vagasLivres, timestamp}`

**Integração com Frontend:**

- Angular se conecta a `ws://localhost:8080/api/ws/estacionamento`
- Recebe atualizações em tempo real
- Dashboard atualiza sem refresh

**Próximas:**

- Início: —
- Conclusão: —

---

### ⏳ **Etapa 7: Integração com Python Service**

**Status:** 🔴 NÃO INICIADA

**O que fazer:**

1. Python Service envia evento JSON via HTTP POST para `/api/eventos`
   ```json
   {
     "tipo": "entrada",
     "estacionamentoId": 1
   }
   ```
2. Backend processa com `EventoService`
3. Backend atualiza banco de dados
4. Backend faz broadcast via WebSocket (Etapa 6)

**Payload esperado:**

```json
{
  "tipo": "entrada ou saida",
  "estacionamentoId": 1
}
```

**Validações:**

- Verificar se estacionamento existe
- Verificar se tipo é válido
- Retornar erro 400 se inválido
- Retornar 200 com evento criado se sucesso

**Python Service repo:**

- Link: [sera fornecido]

**Próximas:**

- Início: —
- Conclusão: —

---

### ⏳ **Etapa 8: Polimento e Documentação**

**Status:** 🔴 NÃO INICIADA

**O que fazer:**

1. Revisar logs estruturados (SLF4J)
2. Implementar Global Exception Handler robusto
3. Validação com Bean Validation (@Valid)
4. Documentar API com comentários
5. Criar `ARCHITECTURE.md` (decisões técnicas)
6. Testar cenários de erro
7. Docker (opcional)
8. Último commit e tag de release

**Próximas:**

- Início: —
- Conclusão: —

---

## 📊 Progresso Geral

```
Etapa 1: [████████████████████] ✅ COMPLETA (Setup)
Etapa 2: [████████████████████] ✅ COMPLETA (Config)
Etapa 3: [████████████████████] ✅ COMPLETA (DB)
Etapa 4: [████████████████████] ✅ COMPLETA (JWT)
Etapa 5: [████████████████████] ✅ COMPLETA (Endpoints)
Etapa 6: [░░░░░░░░░░░░░░░░░░░░] 0% (WebSocket)
Etapa 7: [░░░░░░░░░░░░░░░░░░░░] 0% (Python Integration)
Etapa 8: [░░░░░░░░░░░░░░░░░░░░] 0% (Polishing)
─────────────────────────────────────────────
Total:   [███████████░░░░░░░░░░] 63%
```

---

## 🔗 Outros Repositórios (Já Prontos)

| Componente           | Repo                                   | Status        | Função               |
|----------------------|----------------------------------------|---------------|----------------------|
| **Backend Java**     | `joao4692/CameraMonitoramento_backend` | 🔵 Em Desenv. | API + WebSocket      |
| **Frontend Angular** | [link será adicionado]                 | 🟠 Pronto     | Interface Usuário    |
| **Service Python**   | [link será adicionado]                 | 🟢 Pronto     | Detecção de Veículos |

---

## 🎯 Próximas Ações

### **AGORA:**

1. Começar **Etapa 6 (WebSocket)**
2. Configurar comunicação em tempo real

### **DEPOIS:**

3. Integrar com Python Service (Etapa 7)
4. Testar fluxo completo: Python → Backend → WebSocket → Angular
5. Polir e documentar (Etapa 8)

---

## 💡 Notas Importantes

- **Context-path:** Todas as rotas têm prefixo `/api`
- **Autenticação:** JWT com HS512, chave de 64+ bytes
- **Banco:** PostgreSQL rodando em Docker (localhost:5432)
- **Porta:** Backend em `http://localhost:8080/api`
- **Git:** Commits após cada etapa completada

---

## 📝 Arquivo de Referência

Arquivo original com Q&A sobre JWT e Spring Security:

- `Docs/QA_SISTEMA_ESTACIONAMENTO.md`

---

**Status:** Pronto para Etapa 6 ✅  
**Última atualização:** 09/09/2026
