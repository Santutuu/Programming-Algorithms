package com.example.demo.Dijkstra_EldenRing.services;

// ¡Importamos todas tus clases nuevas!
import com.example.demo.Dijkstra_EldenRing.model.mapa.SitioDeGraciaEntity;
import com.example.demo.Dijkstra_EldenRing.model.mapa.RutaEntity;
import com.example.demo.Dijkstra_EldenRing.repo.SitioDeGraciaRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DijkstraService {

    private final SitioDeGraciaRepository hogueraRepository; // Tu repo

    public DijkstraService(SitioDeGraciaRepository hogueraRepository) { // Tu repo
        this.hogueraRepository = hogueraRepository;
    }

    // ... (El código de ResultadoRuta y NodoCosto queda igual) ...
    public static class ResultadoRuta {
        private final List<String> camino;
        private final int costoTotal;
        public ResultadoRuta(List<String> camino, int costoTotal) {
            this.camino = camino; this.costoTotal = costoTotal;
        }
        public List<String> getCamino() { return camino; }
        public int getCostoTotal() { return costoTotal; }
    }
    private static class NodoCosto implements Comparable<NodoCosto> {
        String nombre; int costo;
        NodoCosto(String nombre, int costo) {
            this.nombre = nombre; this.costo = costo;
        }
        @Override
        public int compareTo(NodoCosto otro) {
            return Integer.compare(this.costo, otro.costo);
        }
    }


    public ResultadoRuta encontrarRutaMasBarata(String nombreInicio, String nombreFin) {

        // 1. TRAER EL GRAFO
        List<SitioDeGraciaEntity> todasLasHogueras = hogueraRepository.findAll().collectList().block(); // Tu clase

        Map<String, SitioDeGraciaEntity> mapa = todasLasHogueras.stream() // Tu clase
                .collect(Collectors.toMap(SitioDeGraciaEntity::getName, hoguera -> hoguera)); // Tu clase

        // 2. PREPARAR ESTRUCTURAS DIJKSTRA
        Map<String, Integer> costos = new HashMap<>();
        Map<String, String> padres = new HashMap<>();
        PriorityQueue<NodoCosto> pq = new PriorityQueue<>();
        Set<String> visitados = new HashSet<>();

        for (SitioDeGraciaEntity hoguera : mapa.values()) { // Tu clase
            costos.put(hoguera.getName(), Integer.MAX_VALUE);
        }

        costos.put(nombreInicio, 0);
        pq.add(new NodoCosto(nombreInicio, 0));

        // 3. EJECUTAR EL ALGORITMO
        while (!pq.isEmpty()) {
            NodoCosto actual = pq.poll();
            String nombreActual = actual.nombre;

            if (visitados.contains(nombreActual)) continue;
            visitados.add(nombreActual);

            if (nombreActual.equals(nombreFin)) break;

            SitioDeGraciaEntity hogueraActual = mapa.get(nombreActual); // Tu clase
            if (hogueraActual == null || hogueraActual.getRutas() == null) continue;

            for (RutaEntity rutaVecina : hogueraActual.getRutas()) { // Tu clase
                SitioDeGraciaEntity vecino = rutaVecina.getDestino(); // Tu clase
                String nombreVecino = vecino.getName();
                int costoRuta = rutaVecina.getEnemigos();

                int nuevoCosto = costos.get(nombreActual) + costoRuta;
                if (nuevoCosto < costos.get(nombreVecino)) {
                    costos.put(nombreVecino, nuevoCosto);
                    padres.put(nombreVecino, nombreActual);
                    pq.add(new NodoCosto(nombreVecino, nuevoCosto));
                }
            }
        }

        // 4. RECONSTRUIR EL CAMINO
        List<String> camino = new ArrayList<>();
        String pasoActual = nombreFin;

        if (costos.get(nombreFin) == Integer.MAX_VALUE) {
            return new ResultadoRuta(List.of("No se encontró ruta"), -1);
        }
        while (pasoActual != null) {
            camino.add(pasoActual);
            pasoActual = padres.get(pasoActual);
        }
        Collections.reverse(camino);

        return new ResultadoRuta(camino, costos.get(nombreFin));
    }
}