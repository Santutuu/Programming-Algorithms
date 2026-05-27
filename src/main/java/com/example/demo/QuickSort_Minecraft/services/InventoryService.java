package com.example.demo.QuickSort_Minecraft.services;

// 🌟 IMPORT CORREGIDO: Aseguramos que Spring encuentre el repositorio relacional
import com.example.demo.QuickSort_Minecraft.model.ItemEntity;
import com.example.demo.QuickSort_Minecraft.repo.ItemRepository;
import com.example.demo.QuickSort_Minecraft.util.InventorySortUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final ItemRepository itemRepository;

    public InventoryService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Devuelve el inventario ordenado por cantidad usando QuickSort.
     */
    public List<ItemEntity> getInventorySortedByQuantity() {
        // 🌟 CORREGIDO: JpaRepository ya retorna un List<ItemEntity> directo, sin reactividad
        List<ItemEntity> items = itemRepository.findAll();

        if (items != null && !items.isEmpty()) {
            // Pasamos la lista mutáable traída de Postgres a la utilidad de ordenamiento
            InventorySortUtil.sortByQuantity(items);
        }

        return items;
    }

    /**
     * Crea o actualiza un ítem en PostgreSQL.
     */
    public ItemEntity createOrUpdateItem(ItemEntity item) {
        // 🌟 CORREGIDO: Se elimina el .block()
        return itemRepository.save(item);
    }

    /**
     * Devuelve todos los ítems de la base de datos sin ordenar.
     */
    public List<ItemEntity> getAllItems() {
        // 🌟 CORREGIDO: Se elimina el .collectList().block()
        return itemRepository.findAll();
    }
}