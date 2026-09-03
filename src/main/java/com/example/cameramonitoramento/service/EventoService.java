package com.example.cameramonitoramento.service;

import com.example.cameramonitoramento.model.Estacionamento;
import com.example.cameramonitoramento.model.Eventos;
import com.example.cameramonitoramento.repository.EventoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventoService {

    private static final Logger logger = LoggerFactory.getLogger(EventoService.class);

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EstacionamentoService estacionamentoService;

    /**
     * Registra um evento de entrada ou saída
     * Atualiza o status do estacionamento
     */
    public Eventos registrarEvento(String tipo, Long estacionamentoId) {
        if (tipo == null) {
            logger.warn("Tipo de evento nulo para estacionamento {}", estacionamentoId);
            throw new RuntimeException("Tipo de evento não pode ser nulo");
        }

        String tipoNormalizado = tipo.trim().toLowerCase();

        if (!tipoNormalizado.equals("entrada") && !tipoNormalizado.equals("saida")) {
            logger.warn("Tipo de evento inválido: {}", tipo);
            throw new RuntimeException("Tipo de evento inválido. Use 'entrada' ou 'saida'");
        }

        try {
            Estacionamento estacionamento = estacionamentoService.buscarPorId(estacionamentoId);

            if (tipoNormalizado.equals("entrada")) {
                logger.info("Processando ENTRADA no estacionamento {}", estacionamentoId);
                estacionamentoService.registrarEntradaVeiculo(estacionamentoId);
            }

            if (tipoNormalizado.equals("saida")) {
                logger.info("Processando SAÍDA no estacionamento {}", estacionamentoId);
                estacionamentoService.registrarSaidaVeiculo(estacionamentoId);
            }

            Eventos evento = new Eventos();
            evento.setTipoEvento(tipoNormalizado);
            evento.setDataHora(LocalDateTime.now());
            evento.setDescricao("Evento de " + tipoNormalizado + " registrado");
            evento.setEstacionamento(estacionamento);

            Eventos eventoSalvo = eventoRepository.save(evento);

            logger.info("Evento registrado com sucesso: tipo={}, estacionamentoId={}, eventoId={}",
                    tipoNormalizado, estacionamentoId, eventoSalvo.getId());

            return eventoSalvo;

        } catch (RuntimeException e) {
            logger.error("Erro ao registrar evento: {}", e.getMessage());
            throw e;
        }
    }
}