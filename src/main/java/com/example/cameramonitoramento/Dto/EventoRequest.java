package com.example.cameramonitoramento.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequest {

    private String tipo;  //entrada ou saida
    private Long estacionamentoId;
}
