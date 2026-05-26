package com.example.demo.BFS_DFS_Pacman.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.stereotype.Component;

@Component
public class PacmanDFS {

    public Map<String, Object> explorarLaberintoConMetricas(int[][] laberinto, int[] inicio, int[] objetivo) {
        Map<String, Object> resultado = new HashMap<>();
        
        if (laberinto == null || laberinto.length == 0) {
            resultado.put("explorado", new ArrayList<int[]>());
            resultado.put("visitados", new ArrayList<int[]>());
            resultado.put("profundidadMaxima", 0);
            return resultado;
        }
        
        int profundidadMaxima = 0;
        
        Stack<NodoDFS> stack = new Stack<>();
        boolean[][] visited = new boolean[laberinto.length][laberinto[0].length];
        List<int[]> explorado = new ArrayList<>();
        List<int[]> visitados = new ArrayList<>();
        
        stack.push(new NodoDFS(inicio[0], inicio[1], 0, null));
        visited[inicio[0]][inicio[1]] = true;
        visitados.add(new int[]{inicio[0], inicio[1]});
        
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        
        while (!stack.isEmpty()) {
            NodoDFS current = stack.pop();
            explorado.add(new int[]{current.x, current.y});
            
            // Actualizar profundidad máxima
            profundidadMaxima = Math.max(profundidadMaxima, current.profundidad);
            
            // Si llegamos al objetivo
            if (current.x == objetivo[0] && current.y == objetivo[1]) {
                List<int[]> camino = reconstruirCaminoDFS(current);
                
                resultado.put("explorado", camino);
                resultado.put("visitados", visitados);
                resultado.put("profundidadMaxima", profundidadMaxima);
                return resultado;
            }
            
            // Explorar vecinos en orden inverso para DFS estándar
            for (int i = directions.length - 1; i >= 0; i--) {
                int[] dir = directions[i];
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];
                
                if (esValido(laberinto, newX, newY) && !visited[newX][newY] && laberinto[newX][newY] != 1) {
                    visited[newX][newY] = true;
                    stack.push(new NodoDFS(newX, newY, current.profundidad + 1, current));
                    visitados.add(new int[]{newX, newY});
                }
            }
        }
        
        // No se encontró camino
        resultado.put("explorado", explorado);
        resultado.put("visitados", visitados);
        resultado.put("profundidadMaxima", profundidadMaxima);
        return resultado;
    }
    
    private boolean esValido(int[][] laberinto, int x, int y) {
        return x >= 0 && x < laberinto.length && 
               y >= 0 && y < laberinto[0].length;
    }
    
    private List<int[]> reconstruirCaminoDFS(NodoDFS objetivo) {
        List<int[]> camino = new ArrayList<>();
        NodoDFS current = objetivo;
        
        while (current != null) {
            camino.add(0, new int[]{current.x, current.y});
            current = current.padre;
        }
        
        return camino;
    }
    
    static class NodoDFS {
        int x, y, profundidad;
        NodoDFS padre;
        
        NodoDFS(int x, int y, int profundidad, NodoDFS padre) {
            this.x = x;
            this.y = y;
            this.profundidad = profundidad;
            this.padre = padre;
        }
    }
}