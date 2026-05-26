package com.example.demo.BranchAndBound_EldenRing.controller;

import com.example.demo.BranchAndBound_EldenRing.model.ArmorEntity;
import com.example.demo.BranchAndBound_EldenRing.services.BranchAndBoundService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/armor")
public class ArmorController {

    private final BranchAndBoundService armorService;

    public ArmorController(BranchAndBoundService armorService) {
        this.armorService = armorService;
    }

    /**
     * Endpoint para encontrar el loadout óptimo.
     * Algoritmo: Branch & Bound
     * Ejemplo: http://localhost:8080/armor/optimize?capacity=70
     */
    @GetMapping("/optimize")
    public BranchAndBoundService.OptimizationResult getOptimizedLoadout(
            @RequestParam int capacity) {

        return armorService.optimizarLoadout(capacity);
    }

    /**
     * Endpoint para obtener TODAS las armaduras (para la demo visual)
     */
    @GetMapping("/all")
    public List<ArmorEntity> getAllArmor() {
        return armorService.getAllArmor();
    }

    /**
     * Endpoint para agregar una nueva armadura
     */
    @PostMapping("/add")
    public void addArmor(@RequestBody ArmorEntity armor) {
        armorService.addArmor(armor);
    }
}