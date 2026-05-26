package com.example.demo.BranchAndBound_EldenRing.repo;

import com.example.demo.BranchAndBound_EldenRing.model.ArmorEntity;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Mono;

// ¡Asegurate de que sea una "interface"!
public interface ArmorRepository extends ReactiveNeo4jRepository<ArmorEntity, String> {

    // ¡ACÁ ESTÁ LA MAGIA!
    // Esta es la "definición" o "anuncio" del método que vamos a usar.
    // Fijate que la consulta busca (a:Armor), que coincide con tu @Node("Armor")
    @Query("MATCH (a:Armor) DETACH DELETE a")
    Mono<Void> deleteAllArmor();

    // ¡Acá NO se escribe saveAll()! Ya viene "heredado".
    // ¡Acá NO va el CommandLineRunner!
}