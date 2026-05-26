package com.example.demo.BFS_DFS_Pacman.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Component;

@Component
public class PacmanBFS {

    public Map<String, Object> encontrarCaminoMasCortoConMetricas(int[][] laberinto, int[] inicio, int[] objetivo) {
        Map<String, Object> resultado = new HashMap<>();
        
        if (laberinto == null || laberinto.length == 0) {
            resultado.put("camino", new ArrayList<int[]>());
            resultado.put("visitados", new ArrayList<int[]>());
            resultado.put("profundidad", 0);
            return resultado;
        }
        
        int nodosExpandidos = 0;
        int profundidad = 0;
        
        Queue<Nodo> queue = new LinkedList<>();
        boolean[][] visited = new boolean[laberinto.length][laberinto[0].length];
        int[][] parentX = new int[laberinto.length][laberinto[0].length];
        int[][] parentY = new int[laberinto.length][laberinto[0].length];
        List<int[]> visitados = new ArrayList<>();
        
        // Inicializar padres
        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[0].length; j++) {
                parentX[i][j] = -1;
                parentY[i][j] = -1;
            }
        }
        
        queue.offer(new Nodo(inicio[0], inicio[1], 0));
        visited[inicio[0]][inicio[1]] = true;
        visitados.add(new int[]{inicio[0], inicio[1]});
        nodosExpandidos++;
        
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        
        while (!queue.isEmpty()) {
            Nodo current = queue.poll();
            
            // Actualizar profundidad máxima
            profundidad = Math.max(profundidad, current.profundidad);
            
            // Si llegamos al objetivo, reconstruir camino
            if (current.x == objetivo[0] && current.y == objetivo[1]) {
                List<int[]> camino = reconstruirCamino(parentX, parentY, objetivo[0], objetivo[1]);
                
                resultado.put("camino", camino);
                resultado.put("visitados", visitados);
                resultado.put("profundidad", profundidad);
                return resultado;
            }
            
            // Explorar vecinos
            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];
                
                if (esValido(laberinto, newX, newY) && !visited[newX][newY] && laberinto[newX][newY] != 1) {
                    visited[newX][newY] = true;
                    queue.offer(new Nodo(newX, newY, current.profundidad + 1));
                    parentX[newX][newY] = current.x;
                    parentY[newX][newY] = current.y;
                    visitados.add(new int[]{newX, newY});
                    nodosExpandidos++;
                }
            }
        }
        
        // No se encontró camino
        resultado.put("camino", new ArrayList<int[]>());
        resultado.put("visitados", visitados);
        resultado.put("profundidad", profundidad);
        return resultado;
    }
    
    private boolean esValido(int[][] laberinto, int x, int y) {
        return x >= 0 && x < laberinto.length && 
               y >= 0 && y < laberinto[0].length;
    }
    
    private List<int[]> reconstruirCamino(int[][] parentX, int[][] parentY, int x, int y) {
        List<int[]> camino = new ArrayList<>();
        
        while (x != -1 && y != -1) {
            camino.add(0, new int[]{x, y});
            int tempX = parentX[x][y];
            int tempY = parentY[x][y];
            x = tempX;
            y = tempY;
        }
        
        return camino;
    }
    
    static class Nodo {
        int x, y, profundidad;
        
        Nodo(int x, int y, int profundidad) {
            this.x = x;
            this.y = y;
            this.profundidad = profundidad;
        }
    }
}
