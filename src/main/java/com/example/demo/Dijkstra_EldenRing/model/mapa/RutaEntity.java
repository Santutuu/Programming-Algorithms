package com.example.demo.Dijkstra_EldenRing.model.mapa;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class RutaEntity { // Tu nombre de clase

    @RelationshipId
    private Long id;

    @TargetNode
    private SitioDeGraciaEntity destino; // ¡Acá usamos tu clase!

    private final int enemigos;

    public RutaEntity(SitioDeGraciaEntity destino, int enemigos) { // ¡Acá también!
        this.destino = destino;
        this.enemigos = enemigos;
    }

    // --- Getters ---
    public SitioDeGraciaEntity getDestino() {
        return destino;
    }

    public int getEnemigos() {
        return enemigos;
    }
}