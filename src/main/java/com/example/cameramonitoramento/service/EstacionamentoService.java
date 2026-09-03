package com.example.cameramonitoramento.service;


import com.example.cameramonitoramento.model.Estacionamento;
import com.example.cameramonitoramento.repository.EstacionamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class EstacionamentoService {

    private static final Logger logger = LoggerFactory.getLogger(EstacionamentoService.class);

    @Autowired

    private EstacionamentoRepository estacionamentoRepository;


    public Estacionamento buscarPorId(Long id) {
        Optional<Estacionamento> estacionamento = estacionamentoRepository.findById(id);

        if (estacionamento.isEmpty()) {
            throw new RuntimeException("Estacionamento não encontrado");
        }
        return estacionamento.get();
    }


    //CALCULAR QUANTAS VAGAS LIVRES
    public Integer podeEnINtrar(Estacionamento estacionamento) {
        return estacionamento.getTotalVagas() - estacionamento.getVagasOcupadas();
    }

    //VERIFICAR SE PODE ENTRAR UM VEICULO NOVO
    public boolean podeEntrar(Estacionamento estacionamento) {

        return estacionamento.getVagasOcupadas() < estacionamento.getTotalVagas();
    }

    //REGISTRAR ENTRADA DE VEICULO
    public void registrarEntradaVeiculo(Long estacionamentoId) {
        Estacionamento estacionamento = buscarPorId(estacionamentoId);
        if (!podeEntrar(estacionamento)) {
            logger.warn("Não é possível registrar entrada, estacionamento está cheio");
            throw new RuntimeException("Não é possível registrar entrada, estacionamento está cheio");
        }
        //INCREMENTAR VAGAS OCUPADAS
        estacionamento.setVagasOcupadas(estacionamento.getVagasOcupadas() + 1);

        //SALVAR NO BANCO DE DADOS
        estacionamentoRepository.save(estacionamento);

        logger.info("Entrada registrada no estacionamento {}. Vagas ocupadas agora: {}",
                estacionamentoId, estacionamento.getVagasOcupadas());
    }


    //REGISTRAR SAIDA DE VEICULO
    public void registrarSaidaVeiculo(Long estacionamentoId) {
        Estacionamento estacionamento = buscarPorId(estacionamentoId);

        if (estacionamento.getVagasOcupadas() <= 0) {
            logger.warn("Não é possível registrar saída, estacionamento está vazio");
            throw new RuntimeException("Não é possível registrar saída, estacionamento está vazio");
        }
        //DECREMENTAR VAGAS OCUPADAS
        estacionamento.setVagasOcupadas(estacionamento.getVagasOcupadas() - 1);


        //SALVAR NO BANCO DE DADOS
        estacionamentoRepository.save(estacionamento);

        logger.info("Saída registrada no estacionamento {}. Vagas ocupadas agora: {}",
                estacionamentoId, estacionamento.getVagasOcupadas());
    }
}
