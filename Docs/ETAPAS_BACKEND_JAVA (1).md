# 🚗 Sistema de Estacionamento Inteligente — Backend Java + Spring Boot

**Projeto:** CameraMonitoramento  
**Stack:** Java + Spring Boot + Maven + PostgreSQL + RabbitMQ (Message Broker)  
**Responsável:** JU (Joana Psicólogo)  
**Data de Início:** 02/09/2026  
**Status Geral:** ⏳ Iniciando  

---

## 📌 Escopo deste Documento

Este documento rastreia **APENAS o Backend Java** do sistema de estacionamento. 

**O que NÃO está aqui:**
- Frontend Angular (será feito em outro lugar)
- Serviço Python (você já tem pronto)

**O que ESTÁ aqui:**
- API REST + WebSocket (processa eventos do Python)
- Banco de dados (PostgreSQL)
- Autenticação (JWT)
- Message Broker (RabbitMQ ou Redis) — fila de eventos
- Segurança e logs

---

## 🏗️ Arquitetura da Solução

```
Python (camera-service)
    ↓ envia evento via WebSocket
[Message Broker — RabbitMQ/Redis] ← fila de entrada/saída
    ↓
Spring Boot (backend)
    ├─ Valida evento (Zod/Bean Validation)
    ├─ Processa regra de negócio (atualiza vagas)
    ├─ Persiste no banco (PostgreSQL)
    └─ Broadcast para Angular (WebSocket)
    ↓
Angular (frontend)
    └─ mostra status em tempo real
```

**Por que fila?**
- Desacopla Python do Backend — Python não precisa esperar resposta
- Resiliente — eventos não se perdem se o backend cair
- Escalável — pode processar múltiplos eventos em paralelo
- Portfólio — mostra arquitetura profissional

---

## 🎯 Etapas de Desenvolvimento

### ✅ Etapa 1: Git + Estrutura Inicial

**Objetivo:** Inicializar repositório Git e criar estrutura base do projeto

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Inicializar Git no projeto (`git init`)
- [ ] Criar `.gitignore` (Maven + IDE)
- [ ] Criar `README.md` inicial (descrever o backend)
- [ ] Primeiro commit

**O que vai fazer:**
- **Pasta:** Raiz do projeto `CameraMonitoramento/`
- **Criar:** `.gitignore`, `README.md`
- **Comando:** `git init`, `git add`, `git commit`

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** —  

---

### ✅ Etapa 2: Spring Boot Base + Dependências

**Objetivo:** Configurar projeto Spring com dependências essenciais

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Verificar dependências Maven já adicionadas
- [ ] Adicionar dependências faltantes (Spring Web, JPA, PostgreSQL, RabbitMQ, JWT, WebSocket)
- [ ] Criar estrutura de pastas (controller, service, repository, model, config, etc.)
- [ ] Configurar `application.properties` (banco, porta, RabbitMQ)
- [ ] Testar servidor subindo sem erros

**Dependências esperadas:**
- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Spring AMQP (RabbitMQ)
- Spring WebSocket
- Spring Security + JWT
- Lombok
- Spring Boot DevTools

**O que vai fazer:**
- **Arquivo:** `pom.xml` (adicionar dependências faltantes)
- **Arquivo:** `src/main/resources/application.properties` (configurar)
- **Pasta:** `src/main/java/com/example/cameramonitoramento/` (estrutura MVC)

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** —  

---

### ✅ Etapa 3: Banco de Dados (PostgreSQL + JPA)

**Objetivo:** Configurar PostgreSQL e criar models (Estacionamento, Evento, Usuario)

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Configurar conexão PostgreSQL em `application.properties`
- [ ] Criar model `Estacionamento` (@Entity)
- [ ] Criar model `Evento` (@Entity)
- [ ] Criar model `Usuario` (@Entity)
- [ ] Criar repositories (JpaRepository)
- [ ] Executar migrations (Hibernate ou Flyway)
- [ ] Seed inicial (1 estacionamento de teste)

**O que vai fazer:**
- **Pasta:** `src/main/java/.../model/`
  - `Estacionamento.java`
  - `Evento.java`
  - `Usuario.java`
- **Pasta:** `src/main/java/.../repository/`
  - `EstacionamentoRepository.java`
  - `EventoRepository.java`
  - `UsuarioRepository.java`

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** —  

---

### ✅ Etapa 4: Autenticação JWT (Spring Security)

**Objetivo:** Implementar login de admin + geração de JWT + proteção de rotas

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Criar controller de autenticação (POST /auth/login)
- [ ] Implementar JWT Provider (gerar e validar tokens)
- [ ] Configurar Spring Security
- [ ] Criar middleware/filtro de JWT
- [ ] Proteger rotas administrativas (@PreAuthorize)
- [ ] Testar login + geração de token

**O que vai fazer:**
- **Pasta:** `src/main/java/.../controller/`
  - `AuthController.java`
- **Pasta:** `src/main/java/.../security/`
  - `JwtProvider.java`
  - `JwtAuthenticationFilter.java`
  - `SecurityConfig.java`
- **Pasta:** `src/main/java/.../service/`
  - `AuthService.java`

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** —  

---

### ✅ Etapa 5: Regras de Negócio (Estacionamento)

**Objetivo:** Endpoints públicos e lógica de atualização de vagas

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Criar `EstacionamentoService` (lógica de negócio)
- [ ] Criar `EventoService` (processamento de eventos)
- [ ] Criar `EstacionamentoController` (rotas públicas)
- [ ] Endpoint: GET `/api/public/estacionamentos/{id}` (status de vagas)
- [ ] Endpoint: POST `/api/eventos` (receber evento — será chamado pela fila)
- [ ] Validação com Bean Validation (@Valid, @NotNull, etc.)
- [ ] Lógica: entrada aumenta vagas_ocupadas, saída diminui

**O que vai fazer:**
- **Pasta:** `src/main/java/.../service/`
  - `EstacionamentoService.java`
  - `EventoService.java`
- **Pasta:** `src/main/java/.../controller/`
  - `EstacionamentoController.java`

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** —  

---

### ✅ Etapa 6: Message Broker (RabbitMQ ou Redis)

**Objetivo:** Fila de eventos — Python envia, Backend consome

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Escolher Message Broker (RabbitMQ recomendado, ou Redis)
- [ ] Instalar/Configurar localmente
- [ ] Configurar Spring AMQP em `application.properties`
- [ ] Criar `EventoListener` (escuta a fila)
- [ ] Implementar `EventoProducer` (envia para fila — se necessário)
- [ ] Testar: Python envia → fila recebe → Backend processa
- [ ] Implementar retry/dead-letter queue (se evento falhar)

**O que vai fazer:**
- **Arquivo:** `src/main/resources/application.properties` (RabbitMQ config)
- **Pasta:** `src/main/java/.../messaging/`
  - `EventoListener.java` (consome eventos)
  - `EventoProducer.java` (opcional — produz eventos internos)
  - `RabbitConfig.java` (configuração de filas)

**Notas:**
- RabbitMQ é mais robusto; Redis é mais simples
- Decida qual usar antes de começar esta etapa

**Data de Início:** —  
**Data de Conclusão:** —  

---

### ✅ Etapa 7: WebSocket (Spring WebSocket)

**Objetivo:** Broadcast em tempo real para Angular

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Configurar Spring WebSocket em `WebSocketConfig.java`
- [ ] Criar handler de WebSocket (`EstacionamentoWebSocketHandler.java`)
- [ ] Implementar broadcast de atualização de vagas
- [ ] Clientes Angular se conectam em `/ws/estacionamento`
- [ ] Validar payload recebido (Bean Validation)
- [ ] Testar: evento chega → vagas atualizam → Angular recebe broadcast

**O que vai fazer:**
- **Pasta:** `src/main/java/.../config/`
  - `WebSocketConfig.java`
- **Pasta:** `src/main/java/.../websocket/`
  - `EstacionamentoWebSocketHandler.java`

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** —  

---

### ✅ Etapa 8: Polimento (Portfólio-Ready)

**Objetivo:** Logs, tratamento de erros, documentação, testes

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Revisar logs estruturados (SLF4J)
- [ ] Criar Global Exception Handler
- [ ] Documentar API (Swagger/OpenAPI, opcional)
- [ ] Criar `ARCHITECTURE.md` (explicar fluxo + decisões)
- [ ] Testar cenários de erro (fila offline, banco offline, etc.)
- [ ] README.md completo (setup, como rodar, prints)
- [ ] Últimos commits e tags de release

**O que vai fazer:**
- **Pasta:** `src/main/java/.../exception/`
  - `GlobalExceptionHandler.java`
- **Arquivo:** `README.md` (atualizar com setup completo)
- **Arquivo:** `ARCHITECTURE.md` (novo)

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** —  

---

## 📊 Progresso Geral

```
Etapa 1 (Git):           [                    ] 0%
Etapa 2 (Spring Base):   [                    ] 0%
Etapa 3 (Banco):         [                    ] 0%
Etapa 4 (JWT):           [                    ] 0%
Etapa 5 (Negócio):       [                    ] 0%
Etapa 6 (Fila):          [                    ] 0%
Etapa 7 (WebSocket):     [                    ] 0%
Etapa 8 (Polimento):     [                    ] 0%
─────────────────────────────────────────────
Total:                   [                    ] 0%
```

---

## 📝 Decisões Técnicas Registradas

| Tema | Decisão | Data | Motivo |
|------|---------|------|--------|
| Stack Backend | Java + Spring Boot + Maven | 02/09/2026 | Performance, robustez, portfólio |
| Banco | PostgreSQL + JPA/Hibernate | 02/09/2026 | Confiável, escalável |
| Message Broker | RabbitMQ (ou Redis) | 02/09/2026 | Resiliente, desacopla componentes |
| Autenticação | JWT + Spring Security | 02/09/2026 | Stateless, RESTful |
| WebSocket | Spring WebSocket | 02/09/2026 | Nativo, integrado |
| Validação | Bean Validation | 02/09/2026 | Padrão Spring |

---

## 📌 Fluxo de Dados (Referência)

```
1. Python detecta veículo
   └─> envia evento via WebSocket: { tipo, estacionamento_id, timestamp }

2. Spring recebe em Listener
   └─> valida (Bean Validation)
   └─> processa (atualiza contador)
   └─> persiste no banco

3. Spring broadcast para Angular
   └─> WebSocket envia: { total_vagas, vagas_ocupadas, vagas_livres }

4. Angular mostra em tempo real
   └─> atualiza UI
```

---

## 🔗 Referências

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring AMQP + RabbitMQ](https://spring.io/projects/spring-amqp)
- [Spring WebSocket](https://spring.io/guides/gs/messaging-stomp-websocket/)
- [Spring Security + JWT](https://spring.io/guides/topicals/spring-security-architecture)

---

## 📌 Notas Importantes

1. **Foco no aprendizado:** Cada etapa explica o porquê, não só o código.
2. **Parar e perguntar:** Ao fim de cada etapa, espaço para dúvidas antes de seguir.
3. **Documentação:** Este arquivo é seu guia — mantenha atualizado.
4. **Portfólio:** Tudo que você fizer aqui fica documentado e no GitHub.

---

**Próximo passo:** Etapa 1 — Git + Estrutura Inicial

