package com.example.demo.QuickSort_Minecraft.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.QuickSort_Minecraft.model.ItemEntity; // 🌟 Que importe ItemEntity

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, String> { 
    // 🌟 ¡OJO ACÁ! Tenía que ser <ItemEntity, String> y no <ArmorEntity, String>
}