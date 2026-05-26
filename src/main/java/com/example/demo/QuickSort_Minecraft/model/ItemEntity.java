package com.example.demo.QuickSort_Minecraft.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Item") // Lo guardamos en Neo4j como un nodo "Item"
public class ItemEntity {

    @Id
    private final String name; // ej: "Pico de Diamante", "Redstone"

    private final Integer quantity; // ej: 1, 64

    private final String category; // ej: "Herramienta", "Recurso"

    // Constructor
    public ItemEntity(String name, Integer quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    // Getters (¡Importante! Los necesitamos para el Comparator)
    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }
}