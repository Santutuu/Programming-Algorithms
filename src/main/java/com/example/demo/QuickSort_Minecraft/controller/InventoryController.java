package com.example.demo.QuickSort_Minecraft.controller;

import com.example.demo.QuickSort_Minecraft.model.ItemEntity;
import com.example.demo.QuickSort_Minecraft.services.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory") // La URL base será /inventory
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Endpoint para obtener el inventario ordenado por cantidad.
     * Algoritmo: Quicksort (Divide y Vencerás)
     */
    @GetMapping("/sorted/by-quantity")
    public List<ItemEntity> getSortedInventory() {
        return inventoryService.getInventorySortedByQuantity();
    }

    /**
     * Endpoint para cargar datos de prueba
     */
    @PutMapping("/")
    public ItemEntity createItem(@RequestBody ItemEntity newItem) {
        return inventoryService.createOrUpdateItem(newItem);
    }

    /**
     * Endpoint para obtener TODOS los items (para la demo visual)
     */
    @GetMapping("/all")
    public List<ItemEntity> getAllItems() {
        return inventoryService.getAllItems();
    }
}