package com.example.demo.QuickSort_Minecraft.util;

import com.example.demo.QuickSort_Minecraft.model.ItemEntity;
import java.util.List;

public class InventorySortUtil {

    /**
     * Método público que inicia el ordenamiento
     */
    public static void sortByQuantity(List<ItemEntity> inventory) {
        if (inventory == null || inventory.size() < 2) {
            return;
        }
        quicksort(inventory, 0, inventory.size() - 1);
    }

    /**
     * El método recursivo de Quicksort (Divide y Vencerás)
     */
    private static void quicksort(List<ItemEntity> inventory, int begin, int end) {
        if (begin < end) {
            // 1. Divide: Encuentra el punto de partición
            int partitionIndex = partition(inventory, begin, end);

            // 2. Vencerás (Conquer): Ordena recursivamente las dos mitades
            quicksort(inventory, begin, partitionIndex - 1);
            quicksort(inventory, partitionIndex + 1, end);
        }
    }

    /**
     * El método de partición.
     */
    private static int partition(List<ItemEntity> inventory, int begin, int end) {
        // Usamos el último elemento como pivote
        ItemEntity pivot = inventory.get(end);

        // Obtenemos el valor numérico para comparar (¡esta es la parte clave!)
        int pivotQuantity = pivot.getQuantity();

        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            // Comparamos directamente la cantidad del item actual (j)
            // con la cantidad del pivote.
            if (inventory.get(j).getQuantity() <= pivotQuantity) {
                i++;
                // 3. Combinar: Hacemos el swap manualmente
                swap(inventory, i, j);
            }
        }

        // Pone el pivote en su lugar correcto (también con swap manual)
        swap(inventory, i + 1, end);
        return i + 1;
    }

    /**
     * Un método de swap manual para no usar Collections.swap
     */
    private static void swap(List<ItemEntity> list, int i, int j) {
        ItemEntity temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}