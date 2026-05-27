package com.example.demo.BranchAndBound_EldenRing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "armors")
public class ArmorEntity {

    @Id
    private String name; // Se quitó el 'final'
    private int peso;    // Se quitó el 'final'
    private int poise;   // Se quitó el 'final'
    private String slot; // Se quitó el 'final'

    // Constructor vacío obligatorio para JPA
    public ArmorEntity() {
    }

    public ArmorEntity(String name, int peso, int poise, String slot) {
        this.name = name;
        this.peso = peso;
        this.poise = poise;
        this.slot = slot;
    }

    // --- Getters y Setters ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPeso() { return peso; }
    public void setPeso(int peso) { this.peso = peso; }

    public int getPoise() { return poise; }
    public void setPoise(int poise) { this.poise = poise; }

    public String getSlot() { return slot; }
    public void setSlot(String slot) { this.slot = slot; }
}