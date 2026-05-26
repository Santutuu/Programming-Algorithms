package com.example.demo.Dijkstra_EldenRing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dijkstra_EldenRing.services.DijkstraService;

@RestController
@RequestMapping("/map")
public class EldenRingMapController {

    private final DijkstraService dijkstraService;

    public EldenRingMapController(DijkstraService dijkstraService) {
        this.dijkstraService = dijkstraService;
    }

    /**
     * Endpoint para encontrar la ruta más segura (menos enemigos).
     * Algoritmo: Dijkstra
     * * Ejemplo de URL:
     * http://localhost:8080/map/route?from=Necrolimbo&to=Farum Azula
     */
    @GetMapping("/route")
    public DijkstraService.ResultadoRuta getRutaMasSegura(
            @RequestParam String from,
            @RequestParam String to) {

        return dijkstraService.encontrarRutaMasBarata(from, to);
    }
}