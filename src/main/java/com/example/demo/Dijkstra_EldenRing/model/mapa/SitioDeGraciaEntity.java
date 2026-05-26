package com.example.demo.Dijkstra_EldenRing.model.mapa;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("SitioDeGracia")
public class SitioDeGraciaEntity { // Tu nombre de clase

    @Id
    private final String name;

    @Relationship(type = "RUTA", direction = Relationship.Direction.OUTGOING)
    private Set<RutaEntity> rutas; // ¡Acá usamos tu clase de Ruta!

    public SitioDeGraciaEntity(String name) {
        this.name = name;
        this.rutas = new HashSet<>();
    }

    // Método para agregar caminos
    public void addRuta(SitioDeGraciaEntity destino, int costoEnemigos) { // ¡Usamos tus clases!
        this.rutas.add(new RutaEntity(destino, costoEnemigos));
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public Set<RutaEntity> getRutas() { // ¡Usamos tus clases!
        return rutas;
    }
}