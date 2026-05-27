package com.example.demo.Dijkstra_EldenRing.model.mapa;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sitios_de_gracia")
public class SitioDeGraciaEntity {

    @Id
    private String name;

    // CascadeType.ALL y orphanRemoval aseguran que si borrás un sitio, se limpien sus rutas asociadas
    @OneToMany(mappedBy = "origen", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RutaEntity> rutas = new HashSet<>();

    // Constructor vacío obligatorio para JPA
    public SitioDeGraciaEntity() {}

    public SitioDeGraciaEntity(String name) {
        this.name = name;
    }

    // Método para agregar caminos adaptado a JPA
    public void addRuta(SitioDeGraciaEntity destino, int costoEnemigos) {
        RutaEntity nuevaRuta = new RutaEntity(this, destino, costoEnemigos);
        this.rutas.add(nuevaRuta);
    }

    // --- Getters y Setters ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<RutaEntity> getRutas() { return rutas; }
    public void setRutas(Set<RutaEntity> rutas) { this.rutas = rutas; }
}