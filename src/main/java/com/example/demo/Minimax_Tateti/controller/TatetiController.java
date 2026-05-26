package com.example.demo.Minimax_Tateti.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Minimax_Tateti.service.TatetiService;

@RestController
@RequestMapping("/tateti")
public class TatetiController {

    private final TatetiService tatetiService;

    public TatetiController(TatetiService tatetiService) {
        this.tatetiService = tatetiService;
    }

    /**
     * Endpoint de prueba para verificar que el backend responde
     */
    @GetMapping("/test")
    public Map<String, String> testBackend() {
        return Collections.singletonMap("status", "ok");
    }

    /**
     * Endpoint para comenzar un nuevo juego
     */
    @PostMapping("/new")
    public Map<String, Object> newGame() {
        return tatetiService.resetGame();
    }

    /**
     * Endpoint para que el jugador humano haga un movimiento
     */
    @PostMapping("/move")
    public Map<String, Object> playerMove(@RequestBody Map<String, Integer> moveRequest) {
        int fila = moveRequest.get("fila");
        int columna = moveRequest.get("columna");
        return tatetiService.makePlayerMove(fila, columna);
    }

    /**
     * Endpoint para obtener el estado actual del juego
     */
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        return tatetiService.getGameState();
    }
}