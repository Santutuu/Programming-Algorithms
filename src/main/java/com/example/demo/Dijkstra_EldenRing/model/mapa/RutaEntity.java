package com.example.demo.Dijkstra_EldenRing.model.mapa;

import jakarta.persistence.*;

@Entity
@Table(name = "rutas")
public class RutaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origen_name", nullable = false)
    private SitioDeGraciaEntity origen;

    @ManyToOne
    @JoinColumn(name = "destino_name", nullable = false)
    private SitioDeGraciaEntity destino;

    private int enemigos;

    // Constructor vacío obligatorio para JPA
    public RutaEntity() {}

    public RutaEntity(SitioDeGraciaEntity origen, SitioDeGraciaEntity destino, int enemigos) {
        this.origen = origen;
        this.destino = destino;
        this.enemigos = enemigos;
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    
    public SitioDeGraciaEntity getOrigen() { return origen; }
    public void setOrigen(SitioDeGraciaEntity origen) { this.origen = origen; }

    public SitioDeGraciaEntity getDestino() { return destino; }
    public void setDestino(SitioDeGraciaEntity destino) { this.destino = destino; }

    public int getEnemigos() { return enemigos; }
    public void setEnemigos(int enemigos) { this.enemigos = enemigos; }
}