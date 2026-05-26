package com.example.demo.BranchAndBound_EldenRing.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Armor")
public class ArmorEntity {

    @Id
    private final String name;
    private final int peso;
    private final int poise; // El "valor" que queremos maximizar
    private final String slot; // ¡NUEVO! ej: "HEAD", "CHEST", "ARMS", "LEGS"

    public ArmorEntity(String name, int peso, int poise, String slot) {
        this.name = name;
        this.peso = peso;
        this.poise = poise;
        this.slot = slot;
    }

    // --- Getters ---
    public String getName() { return name; }
    public int getPeso() { return peso; }
    public int getPoise() { return poise; }
    public String getSlot() { return slot; }
}