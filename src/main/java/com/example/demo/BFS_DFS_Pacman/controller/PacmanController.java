package com.example.demo.BFS_DFS_Pacman.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BFS_DFS_Pacman.service.PacmanService;

@RestController
@RequestMapping("/pacman")
public class PacmanController {
    
    @Autowired
    private PacmanService pacmanService;
    
    @GetMapping("/laberinto")
    public Map<String, Object> getLaberinto() {
        return pacmanService.getEstadoActual();
    }
    
    @PostMapping("/bfs")
    public Map<String, Object> calcularBFS() {
        return pacmanService.calcularRutaBFS();
    }
    
    @PostMapping("/dfs")
    public Map<String, Object> calcularDFS() {
        return pacmanService.calcularRutaDFS();
    }
    
    @PostMapping("/reset")
    public Map<String, Object> resetear() {
        return pacmanService.resetearLaberinto();
    }
    
    @PostMapping("/cancha/{numero}")
    public Map<String, Object> cambiarCancha(@PathVariable int numero) {
        return pacmanService.cambiarCancha(numero);
    }
}