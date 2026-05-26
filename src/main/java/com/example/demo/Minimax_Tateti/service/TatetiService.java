package com.example.demo.Minimax_Tateti.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.Minimax_Tateti.util.MinimaxAlgorithm;

@Service
public class TatetiService {

    private final MinimaxAlgorithm minimaxAlgorithm;
    private String[][] currentBoard;
    private String currentPlayer;
    private String winner;
    private boolean terminal;

    public TatetiService(MinimaxAlgorithm minimaxAlgorithm) {
        this.minimaxAlgorithm = minimaxAlgorithm;
        resetGame();
    }

    public Map<String, Object> resetGame() {
        this.currentBoard = minimaxAlgorithm.initialState();
        this.currentPlayer = "X";
        this.winner = null;
        this.terminal = false;
        return getGameState();
    }

    public Map<String, Object> makePlayerMove(int fila, int columna) {
        // Validar movimiento
        if (fila < 0 || fila > 2 || columna < 0 || columna > 2) {
            throw new IllegalArgumentException("Posición inválida");
        }
        if (!currentBoard[fila][columna].equals(" ")) {
            throw new IllegalArgumentException("Casilla ocupada");
        }

        // Aplicar movimiento del jugador
        int[] move = {fila, columna};
        this.currentBoard = minimaxAlgorithm.result(currentBoard, move);
        
        // Actualizar estado
        this.terminal = minimaxAlgorithm.terminal(currentBoard);
        this.winner = minimaxAlgorithm.winner(currentBoard);
        this.currentPlayer = "O";
        
        // Si no terminó, es turno de la IA
        if (!terminal) {
            makeAIMove();
        }
        
        return getGameState();
    }

    private void makeAIMove() {
        // La IA usa Minimax para decidir su movimiento
        int[] aiMove = minimaxAlgorithm.minimax(currentBoard, "O");
        
        if (aiMove != null) {
            this.currentBoard = minimaxAlgorithm.result(currentBoard, aiMove);
            this.terminal = minimaxAlgorithm.terminal(currentBoard);
            this.winner = minimaxAlgorithm.winner(currentBoard);
            this.currentPlayer = "X";
        }
    }

    public Map<String, Object> getGameState() {
        Map<String, Object> state = new HashMap<>();
        state.put("board", currentBoard);
        state.put("currentPlayer", currentPlayer);
        state.put("winner", winner);
        state.put("terminal", terminal);
        return state;
    }
}