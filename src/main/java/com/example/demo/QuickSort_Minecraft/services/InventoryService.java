package com.example.demo.QuickSort_Minecraft.services;

import com.example.demo.QuickSort_Minecraft.model.ItemEntity;
import com.example.demo.QuickSort_Minecraft.repo.ItemRepository;
import com.example.demo.QuickSort_Minecraft.util.InventorySortUtil;//mi utilidad
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final ItemRepository itemRepository;

    public InventoryService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemEntity> getInventorySortedByQuantity() {

        List<ItemEntity> items = itemRepository.findAll().collectList().block();

        if (items != null && !items.isEmpty()) {

            // ¡Llamada mucho más directa!
            // Simplemente le pasamos la lista a nuestra utilidad.
            InventorySortUtil.sortByQuantity(items);
        }

        return items;
    }


    public ItemEntity createOrUpdateItem(ItemEntity item) {
        return itemRepository.save(item).block();
    }

    /**
     * Devuelve todos los items de la base de datos, sin ordenar.
     */
    public List<ItemEntity> getAllItems() {
        return itemRepository.findAll().collectList().block();
    }
}