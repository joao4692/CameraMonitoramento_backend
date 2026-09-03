package com.example.cameramonitoramento.repository;

import com.example.cameramonitoramento.model.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EventoRepository extends JpaRepository<Eventos, Long> {


}
