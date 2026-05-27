package com.example.demo.QuickSort_Minecraft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "items") // Nombre de la tabla en Postgres
public class ItemEntity {

    @Id // Clave primaria relacional
    private String name; // Se removió el 'final' obligatorio para JPA

    private Integer quantity; 

    private String category; 

    // Constructor vacío obligatorio que exige JPA/Hibernate para poder instanciar filas
    public ItemEntity() {
    }

    // Constructor completo que usás en tu Seeder de 'DemoApplication'
    public ItemEntity(String name, Integer quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    // --- Setters (Recomendados para el mapeo dinámico de Hibernate) ---
    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}