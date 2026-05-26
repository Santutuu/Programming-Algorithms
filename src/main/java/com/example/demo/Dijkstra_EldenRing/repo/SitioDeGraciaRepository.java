package com.example.demo.Dijkstra_EldenRing.repo;


import com.example.demo.Dijkstra_EldenRing.model.mapa.SitioDeGraciaEntity;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Mono;

public interface SitioDeGraciaRepository extends ReactiveNeo4jRepository<SitioDeGraciaEntity, String> {

    // Consulta de Cypher para limpiar la BD de mapas (para el seeder)
    @Query("MATCH (h:SitioDeGracia) DETACH DELETE h")
    Mono<Void> deleteAllSitiosDeGracia();

}