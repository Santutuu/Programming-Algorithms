package com.example.demo.Dijkstra_EldenRing.repo;

import com.example.demo.Dijkstra_EldenRing.model.mapa.SitioDeGraciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SitioDeGraciaRepository extends JpaRepository<SitioDeGraciaEntity, String> {
    // Los métodos CRUD básicos (save, findAll, deleteAll) ya vienen incluidos y no usan Mono/Flux.
}