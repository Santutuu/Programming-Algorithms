package com.example.demo.BFS_DFS_Pacman.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.BFS_DFS_Pacman.util.PacmanBFS;
import com.example.demo.BFS_DFS_Pacman.util.PacmanDFS;

@Service
public class PacmanService {

    private final PacmanBFS pacmanBFS;
    private final PacmanDFS pacmanDFS;
    
    private int[][] laberinto;
    private int[] pacman;
    private int[] fantasma;
    private List<int[]> caminoBFS;
    private List<int[]> exploradoDFS;
    private List<int[]> visitadosBFS;
    private List<int[]> visitadosDFS;
    private boolean bfsCalculado;
    private boolean dfsCalculado;
    private Map<String, Object> metricasBFS;
    private Map<String, Object> metricasDFS;
    private int canchaActual = 1;

    public PacmanService(PacmanBFS pacmanBFS, PacmanDFS pacmanDFS) {
        this.pacmanBFS = pacmanBFS;
        this.pacmanDFS = pacmanDFS;
        inicializarEstado();
    }

    private void inicializarEstado() {
        cargarCancha(canchaActual);
        
        this.caminoBFS = new ArrayList<>();
        this.exploradoDFS = new ArrayList<>();
        this.visitadosBFS = new ArrayList<>();
        this.visitadosDFS = new ArrayList<>();
        this.bfsCalculado = false;
        this.dfsCalculado = false;
        this.metricasBFS = new HashMap<>();
        this.metricasDFS = new HashMap<>();
    }

    private void cargarCancha(int numeroCancha) {
        switch (numeroCancha) {
            case 1:
                // CANCHA 1: BFS tiene ventaja - Laberinto simétrico
                this.laberinto = new int[][] {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
                    {1,0,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1},
                    {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
                    {1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1},
                    {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
                    {1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1},
                    {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
                    {1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1},
                    {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
                };
                this.pacman = new int[]{1, 1};    // Esquina superior izquierda
                this.fantasma = new int[]{15, 17};  // Esquina inferior derecha
                break;

            case 2:
                // CANCHA 2: DFS tiene ventaja - Laberinto con camino profundo
                this.laberinto = new int[][] {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1},
                    {1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1},
                    {1,0,1,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0,1},
                    {1,0,1,0,1,0,1,1,1,1,1,1,1,0,1,0,1,0,1},
                    {1,0,1,0,1,0,1,0,0,0,0,0,1,0,1,0,1,0,1},
                    {1,0,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1},
                    {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
                    {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
                    {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
                    {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
                    {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
                    {1,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,1,0,1},
                    {1,0,1,0,1,0,1,1,1,1,1,0,1,0,1,0,1,0,1},
                    {1,0,1,0,1,0,0,0,0,0,0,0,1,0,1,0,1,0,1},
                    {1,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
                };
                this.pacman = new int[]{1, 1};    // Esquina superior izquierda  
                this.fantasma = new int[]{15, 17};  // Esquina inferior derecha
                break;
        }
    }

    public Map<String, Object> getEstadoActual() {
        Map<String, Object> estado = new HashMap<>();
        estado.put("laberinto", laberinto);
        estado.put("pacman", pacman);
        estado.put("fantasma", fantasma);
        estado.put("caminoBFS", caminoBFS);
        estado.put("exploradoDFS", exploradoDFS);
        estado.put("visitadosBFS", visitadosBFS);
        estado.put("visitadosDFS", visitadosDFS);
        estado.put("bfsCalculado", bfsCalculado);
        estado.put("dfsCalculado", dfsCalculado);
        estado.put("metricasBFS", metricasBFS);
        estado.put("metricasDFS", metricasDFS);
        estado.put("canchaActual", canchaActual);
        return estado;
    }

    public Map<String, Object> cambiarCancha(int nuevaCancha) {
        if (nuevaCancha >= 1 && nuevaCancha <= 2) {
            this.canchaActual = nuevaCancha;
        }
        return resetearLaberinto();
    }

    public Map<String, Object> resetearLaberinto() {
        inicializarEstado();
        return getEstadoActual();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> calcularRutaBFS() {
        long startTime = System.currentTimeMillis();
        
        Map<String, Object> resultadoBFS = pacmanBFS.encontrarCaminoMasCortoConMetricas(laberinto, fantasma, pacman);
        caminoBFS = (List<int[]>) resultadoBFS.get("camino");
        visitadosBFS = (List<int[]>) resultadoBFS.get("visitados");
        bfsCalculado = true;
        
        long endTime = System.currentTimeMillis();
        
        // Solo métricas esenciales
        metricasBFS.put("nodosVisitados", visitadosBFS.size());
        metricasBFS.put("profundidad", resultadoBFS.get("profundidad"));
        
        return getEstadoActual();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> calcularRutaDFS() {
        long startTime = System.currentTimeMillis();
        
        Map<String, Object> resultadoDFS = pacmanDFS.explorarLaberintoConMetricas(laberinto, fantasma, pacman);
        exploradoDFS = (List<int[]>) resultadoDFS.get("explorado");
        visitadosDFS = (List<int[]>) resultadoDFS.get("visitados");
        dfsCalculado = true;
        
        long endTime = System.currentTimeMillis();
        
        // Solo métricas esenciales
        metricasDFS.put("nodosVisitados", visitadosDFS.size());
        metricasDFS.put("profundidadMaxima", resultadoDFS.get("profundidadMaxima"));
        
        return getEstadoActual();
    }
}