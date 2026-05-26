package com.example.demo.BranchAndBound_EldenRing.services;

import com.example.demo.BranchAndBound_EldenRing.model.ArmorEntity;
import com.example.demo.BranchAndBound_EldenRing.repo.ArmorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BranchAndBoundService {

    private final ArmorRepository armorRepository;

    // Variables globales para guardar la mejor solución
    private int bestPoise;
    private List<ArmorEntity> bestLoadout;

    // Estructuras de datos pre-calculadas
    private List<String> slotsToFill;
    private Map<String, List<ArmorEntity>> itemsBySlot;
    private Map<String, Integer> maxPoisePerSlot;

    // --- ¡ESTE ES EL CONSTRUCTOR! ---
    // Se llama igual que la clase ("ArmorService")
    // y NO tiene tipo de retorno.
    public BranchAndBoundService(ArmorRepository armorRepository) {
        this.armorRepository = armorRepository;
    }

    /**
     * Devuelve todas las armaduras de la base de datos.
     */
    public List<ArmorEntity> getAllArmor() {
        return armorRepository.findAll().collectList().block();
    }

    /**
     * Agrega una nueva armadura a la base de datos.
     */
    public void addArmor(ArmorEntity armor) {
        armorRepository.save(armor).block();
    }


    // --- ESTA ES LA CLASE INTERNA QUE FALTABA ---
    public static class OptimizationResult {
        private final List<ArmorEntity> items;
        private final int totalPeso;
        private final int totalPoise;

        public OptimizationResult(List<ArmorEntity> items, int totalPeso, int totalPoise) {
            this.items = items;
            this.totalPeso = totalPeso;
            this.totalPoise = totalPoise;
        }

        // Getters para que el JSON funcione
        public List<ArmorEntity> getItems() { return items; }
        public int getTotalPeso() { return totalPeso; }
        public int getTotalPoise() { return totalPoise; }
    }


    // --- MÉTODOS DEL SERVICIO ---

    /**
     * Prepara los datos para el algoritmo B&B
     */
    private void setupAlgorithmData() {
        List<ArmorEntity> allItems = armorRepository.findAll().collectList().block();

        itemsBySlot = allItems.stream()
                .collect(Collectors.groupingBy(ArmorEntity::getSlot));

        itemsBySlot.forEach((slot, items) -> {
            items.add(new ArmorEntity("Nada", 0, 0, slot));
        });

        slotsToFill = new ArrayList<>(itemsBySlot.keySet());

        maxPoisePerSlot = new HashMap<>();
        itemsBySlot.forEach((slot, items) -> {
            maxPoisePerSlot.put(slot, items.stream().mapToInt(ArmorEntity::getPoise).max().orElse(0));
        });
    }

    /**
     * Método público para iniciar la optimización.
     * ESTE SÍ devuelve algo: un "OptimizationResult"
     */
    public OptimizationResult optimizarLoadout(int maxPeso) {
        setupAlgorithmData();
        this.bestPoise = 0;
        this.bestLoadout = new ArrayList<>();

        branchAndBound(maxPeso, 0, 0, 0, new ArrayList<>());

        int bestWeight = bestLoadout.stream().mapToInt(ArmorEntity::getPeso).sum();

        // Y ESTE es su "return"
        return new OptimizationResult(bestLoadout, bestWeight, bestPoise);
    }

    /**
     * El algoritmo B&B recursivo.
     * ESTE es un método "void", no devuelve nada.
     */
    private void branchAndBound(int maxPeso, int slotIndex,
                                int currentPeso, int currentPoise,
                                List<ArmorEntity> currentLoadout) {

        if (currentPeso > maxPeso) {
            return; // Poda 1
        }

        if (currentPoise > bestPoise) {
            bestPoise = currentPoise;
            bestLoadout = new ArrayList<>(currentLoadout);
        }

        if (slotIndex == slotsToFill.size()) {
            return; // Base de recursión
        }

        int optimisticPoise = calculateBound(slotIndex, currentPoise);
        if (optimisticPoise <= bestPoise) {
            return; // Poda 2
        }

        String currentSlot = slotsToFill.get(slotIndex);
        List<ArmorEntity> itemsParaEsteSlot = itemsBySlot.get(currentSlot);

        for (ArmorEntity item : itemsParaEsteSlot) {
            currentLoadout.add(item);
            branchAndBound(maxPeso,
                    slotIndex + 1,
                    currentPeso + item.getPeso(),
                    currentPoise + item.getPoise(),
                    currentLoadout);
            currentLoadout.remove(currentLoadout.size() - 1);
        }
    }

    /**
     * Calcula el "Límite Superior Optimista" (Bound).
     * ESTE SÍ devuelve algo: un "int"
     */
    private int calculateBound(int slotIndex, int currentPoise) {
        int bound = currentPoise;
        for (int i = slotIndex; i < slotsToFill.size(); i++) {
            String slot = slotsToFill.get(i);
            bound += maxPoisePerSlot.get(slot);
        }
        return bound; // Y ESTE es su "return"
    }
}