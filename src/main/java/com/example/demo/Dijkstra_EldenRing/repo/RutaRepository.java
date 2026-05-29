package com.example.demo.Dijkstra_EldenRing.repo;

import com.example.demo.Dijkstra_EldenRing.model.mapa.RutaEntity; // Ajusta el paquete según donde esté tu entidad Ruta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends JpaRepository<RutaEntity, Long> {
}
