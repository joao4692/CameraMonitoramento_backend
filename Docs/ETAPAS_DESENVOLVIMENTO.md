# 🚗 Sistema de Estacionamento Inteligente — Plano de Desenvolvimento

**Responsável:** Joao Carlos 
**Stack:** Java + Spring Boot + PostgreSQL + Angular + Python (OpenCV)  
**Objetivo:** Aplicação fullstack para gerenciar vagas de estacionamento em tempo real  
**Data de Início:** 02/09/2026  
**Status Geral:** ⏳ Iniciando  

---

## 📌 Visão Geral do Projeto

Este documento rastreia o desenvolvimento de um sistema de estacionamento inteligente que detecta entrada/saída de veículos via câmera, atualiza vagas em tempo real e oferece interface para admin e clientes públicos.

**Arquitetura:**
- **Backend:** Java + Spring Boot + Maven (substituindo Node.js)
- **Banco:** PostgreSQL + JPA/Hibernate
- **Frontend:** Angular (responsivo)
- **IA (Câmera):** Python + OpenCV (RTSP → depois câmera de celular na fase de teste)
- **Comunicação:** REST API + WebSocket

---

## 🎯 Etapas de Construção

### ✅ Etapa 1: Setup do Projeto (Monorepo)

**Objetivo:** Criar estrutura base de pastas, Git e organização

**Status:** ✅ COMPLETA

**Checklist:**
- [x] Criar pasta raiz do projeto
- [x] Inicializar Git + .gitignore
- [x] Criar estrutura de diretórios (backend / frontend / camera-service)
- [x] Criar projeto Spring Boot no IntelliJ (Maven)
- [x] Criar arquivo README.md inicial
- [x] Primeiro commit

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** 02/09/2026  

---

### ✅ Etapa 2: Backend Spring Boot (Base)

**Objetivo:** Configurar projeto Spring com dependências, estrutura MVC e global error handler

**Status:** ✅ COMPLETA

**Checklist:**
- [x] Adicionar dependências (Spring Web, JPA, PostgreSQL, Lombok, Spring Security, etc.)
- [x] Criar estrutura de pastas (controllers, services, repositories, models, config, etc.)
- [x] Configurar application.properties (banco de dados, porta, perfis)
- [x] Criar classe principal da aplicação (Application.java)
- [x] Implementar Global Exception Handler
- [x] Configurar Logger/SLF4J
- [x] Testar servidor subindo

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** 02/09/2026  

---

### ✅ Etapa 3: Banco de Dados (PostgreSQL + JPA)

**Objetivo:** Configurar Postgres e criar models + migrations

**Status:** ✅ COMPLETA!

**Checklist:**
- [x] PASSO 1: Model Estacionamento criado
- [x] PASSO 2: Model Evento criado
- [x] PASSO 3: Repository Estacionamento criado
- [x] PASSO 4: Repository Evento criado
- [x] PASSO 5: Servidor rodou e criou as tabelas

**Notas:**
- Etapa 3 FINALIZADA! 🚀

**Data de Início:** —  
**Data de Conclusão:** 02/09/2026  

---

### ✅ Etapa 4: Autenticação JWT (Spring Security)

**Objetivo:** Implementar login de admin + JWT + proteção de rotas

**Status:** ✅ COMPLETA

**Checklist:**
- [x] Criar Controller de Autenticação (login)
- [x] Implementar JWT Provider (geração e validação)
- [x] Configurar Spring Security
- [x] Criar Middleware de autenticação
- [x] Proteger rotas administrativas
- [x] Testar login + token

**Notas:**
- Login disponível em `POST /api/auth/login` com as credenciais de desenvolvimento `admin` / `admin123`.
- JWT assinado com HS512; filtro executado antes da autorização e rotas não públicas exigem autenticação.
- Login validado por curl com resposta HTTP 200 e token JWT.

**Data de Início:** —  
**Data de Conclusão:** 02/09/2026  

---

### ✅ Etapa 5: Regras de Negócio (Estacionamento)

**Objetivo:** Endpoints de consulta e lógica de entrada/saída de veículos

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Criar Service: EstacionamentoService
- [ ] Criar Service: EventoService
- [ ] Criar Controller: EstacionamentoController (público)
- [ ] Endpoint: GET /api/public/estacionamentos/{id} (status das vagas)
- [ ] Endpoint: POST /api/eventos (recebe entrada/saída)
- [ ] Lógica: Atualizar contador de vagas
- [ ] Validação com Bean Validation
- [ ] Testar fluxo de entrada e saída

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** —  

---

### ✅ Etapa 6: WebSocket (Spring WebSocket)

**Objetivo:** Comunicação em tempo real com frontend

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Configurar Spring WebSocket
- [ ] Criar Handler de WebSocket
- [ ] Validar payload recebido (Zod → Bean Validation)
- [ ] Broadcast de atualização de vagas para clientes
- [ ] Testar conexão e envio de mensagens

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** —  

---

### ✅ Etapa 7: Serviço Python (Câmera + OpenCV)

**Objetivo:** Capturar frames e enviar eventos via WebSocket

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Setup do ambiente Python (venv, requirements.txt)
- [ ] Instalar OpenCV, dependências
- [ ] Fase de teste: Capturar câmera de celular (não RTSP ainda)
- [ ] Loop de leitura de frames
- [ ] Detecção de movimento/veículo (ou simulação manual)
- [ ] Cliente WebSocket enviando evento JSON
- [ ] Testar envio de eventos para o Node

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** —  

---

### ✅ Etapa 8: Frontend Angular

**Objetivo:** Interface responsiva para clientes e admin

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Setup do projeto Angular
- [ ] Criar componente: Tela Pública (status de vagas)
- [ ] Criar componente: Login Admin
- [ ] Criar componente: Dashboard Admin
- [ ] Consumir API REST (status inicial)
- [ ] Conectar WebSocket (atualizações em tempo real)
- [ ] Responsividade (mobile + desktop)
- [ ] Testar integração com backend

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** —  

---

### ✅ Etapa 9: Polimento (Portfólio-Ready)

**Objetivo:** Finalizar e documentar para portfólio

**Status:** ⏳ Não iniciada

**Checklist:**
- [ ] Revisar logs estruturados (SLF4J)
- [ ] Revisar tratamento de erros (ponta a ponta)
- [ ] Criar README completo (setup, arquitetura, prints)
- [ ] Criar ARCHITECTURE.md (decisões técnicas)
- [ ] Adicionar comentários no código (quando necessário)
- [ ] Testar cenários de erro
- [ ] Deploy local/Docker (opcional)
- [ ] Último commit e tag de release

**Notas:**
- 

**Data de Início:** —  
**Data de Conclusão:** —  

---

## 📊 Progresso Geral

```
Etapa 1: [████████████████████] ✅ COMPLETA
Etapa 2: [████████████████████] ✅ COMPLETA
Etapa 3: [████████████████████] ✅ COMPLETA
Etapa 4: [████████████████████] ✅ COMPLETA
Etapa 5: [░░░░░░░░░░░░░░░░░░░░] 0%
Etapa 6: [                    ] 0%
Etapa 7: [                    ] 0%
Etapa 8: [                    ] 0%
Etapa 9: [                    ] 0%
──────────────────────────────────
Total:   [██████████████████    ] 44%
```

---

## 📝 Decisões Técnicas Registradas

| Tema | Decisão | Data | Motivo |
|------|---------|------|--------|
| Stack Backend | Java + Spring Boot | 02/09/2026 | Aprendizado, performance, robustez |
| ORM | JPA + Hibernate | 02/09/2026 | Padrão Spring, migrações fáceis |
| Banco | PostgreSQL | 02/09/2026 | Confiável, open-source, escalável |
| Autenticação | JWT + Spring Security | 02/09/2026 | Stateless, RESTful |
| WebSocket | Spring WebSocket | 02/09/2026 | Nativo, integrado no Spring |
| Câmera (teste) | Câmera de celular (não RTSP) | 02/09/2026 | Fase inicial, evitar complexidade de rede |
| Validação | Bean Validation | 02/09/2026 | Nativo em Spring, simples |

---

## 🔗 Referências e Links

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [JPA/Hibernate](https://hibernate.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring WebSocket](https://spring.io/guides/gs/messaging-stomp-websocket/)
- [Documento Central do Projeto](./DOCUMENTO_CENTRAL.md)

---

## 📌 Notas Importantes

1. **Parar ao final de cada etapa:** Sempre aguardar feedback/perguntas antes de seguir para a próxima.
2. **Aprendizado em primeiro lugar:** Explicar o porquê de cada decisão, não só entregar código.
3. **Dividir em passos pequenos:** Cada etapa tem sub-tarefas claras.
4. **Documentar tudo:** Qualquer mudança de arquitetura precisa ser registrada aqui.
5. **Portfólio-ready:** Este documento é parte do portfólio — mantê-lo organizado!

---

**Próximo passo:** Etapa 1 — Setup do Projeto (Monorepo)
