# 🚗 Camera Monitoramento — Backend Java

Serviço backend em Java + Spring Boot que processa eventos de entrada/saída de veículos em tempo real.

## 📋 O que faz

- Recebe eventos de entrada/saída de um serviço Python (câmera + OpenCV)
- Processa eventos através de uma **fila de mensagens** (Message Broker)
- Atualiza o banco de dados (PostgreSQL)
- Envia atualizações em tempo real para o frontend Angular via WebSocket

## 🏗️ A
## 🛠️ Stack

- **Java 17+**
- **Spring Boot 4.1+**
- **Maven**
- **PostgreSQL**
- **RabbitMQ** (Message Broker)
- **Spring WebSocket**
- **Spring Security + JWT**

## 👤 Autor

JU (Joao carlos) — Projeto de Portfóliorquitetura