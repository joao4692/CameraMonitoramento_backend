package com.example.cameramonitoramento.controller;

import com.example.cameramonitoramento.Dto.EventoRequest;
import com.example.cameramonitoramento.model.Estacionamento;
import com.example.cameramonitoramento.model.Eventos;
import com.example.cameramonitoramento.service.EstacionamentoService;
import com.example.cameramonitoramento.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class EstacionamentoController {

    @Autowired
    private EstacionamentoService estacionamentoService;

    @Autowired
    private EventoService eventoService;

    @GetMapping("/public/estacionamentos/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        try {
            Estacionamento e = estacionamentoService.buscarPorId(id);
            int livres = estacionamentoService.podeEnINtrar(e);

            Map<String, Object> res = new HashMap<>();
            res.put("id", e.getId());
            res.put("nome", e.getNome());
            res.put("localizacao", e.getLocalizacao());
            res.put("totalVagas", e.getTotalVagas());
            res.put("ocupadas", e.getVagasOcupadas());
            res.put("livres", livres);

            return ResponseEntity.ok(res);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/eventos")
    public ResponseEntity<?> registrar(@RequestBody EventoRequest req) {
        try {
            Eventos ev = eventoService.registrarEvento(req.getTipo(), req.getEstacionamentoId());
            return ResponseEntity.ok(ev);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}