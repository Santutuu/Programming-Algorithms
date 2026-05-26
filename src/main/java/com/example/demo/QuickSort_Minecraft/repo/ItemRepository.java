package com.example.demo.QuickSort_Minecraft.repo;

import com.example.demo.QuickSort_Minecraft.model.ItemEntity;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

// Usamos el repositorio reactivo como pide el ejemplo
public interface ItemRepository extends ReactiveNeo4jRepository<ItemEntity, String> {
    // Con esto ya tenemos el .findAll()
}