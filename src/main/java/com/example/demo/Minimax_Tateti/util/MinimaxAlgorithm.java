package com.example.demo.Minimax_Tateti.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class MinimaxAlgorithm {
    
    private static final String X = "X";
    private static final String O = "O";
    private static final String EMPTY = " ";

    public String[][] initialState() {
        return new String[][] {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
        };
    }

    public String player(String[][] board) {
        int contX = 0;
        int contO = 0;
        for (String[] fila : board) {
            for (String c : fila) {
                if (c.equals(O)) contO++;
                if (c.equals(X)) contX++;
            }
        }
        return (contX > contO) ? O : X;
    }

    public Set<int[]> actions(String[][] board) {
        Set<int[]> acciones = new HashSet<>();
        if (terminal(board)) return acciones;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(EMPTY)) {
                    acciones.add(new int[]{i, j});
                }
            }
        }
        return acciones;
    }

    public String[][] result(String[][] board, int[] action) {
        String turno = player(board);
        int fila = action[0];
        int columna = action[1];

        String[][] newBoard = new String[3][3];
        for (int i = 0; i < 3; i++)
            newBoard[i] = board[i].clone();

        if (!newBoard[fila][columna].equals(EMPTY))
            throw new IllegalArgumentException("Acción inválida");

        newBoard[fila][columna] = turno;
        return newBoard;
    }

    public String winner(String[][] board) {
        // Filas
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].equals(EMPTY) &&
                board[i][0].equals(board[i][1]) &&
                board[i][1].equals(board[i][2]))
                return board[i][0];
        }
        // Columnas
        for (int j = 0; j < 3; j++) {
            if (!board[0][j].equals(EMPTY) &&
                board[0][j].equals(board[1][j]) &&
                board[1][j].equals(board[2][j]))
                return board[0][j];
        }
        // Diagonales
        if (!board[0][0].equals(EMPTY) &&
            board[0][0].equals(board[1][1]) &&
            board[1][1].equals(board[2][2]))
            return board[0][0];

        if (!board[0][2].equals(EMPTY) &&
            board[0][2].equals(board[1][1]) &&
            board[1][1].equals(board[2][0]))
            return board[0][2];

        return null;
    }

    public boolean terminal(String[][] board) {
        if (winner(board) != null) return true;

        for (String[] fila : board) {
            for (String c : fila) {
                if (c.equals(EMPTY))
                    return false;
            }
        }
        return true;
    }

    public int utility(String[][] board) {
        String resultado = winner(board);
        if (resultado == null) return 0;
        if (resultado.equals(X)) return 1;
        if (resultado.equals(O)) return -1;
        return 0;
    }

    private int maxValue(String[][] board) {
        if (terminal(board)) return utility(board);
        int v = Integer.MIN_VALUE;

        for (int[] action : actions(board)) {
            v = Math.max(v, minValue(result(board, action)));
        }
        return v;
    }

    private int minValue(String[][] board) {
        if (terminal(board)) return utility(board);
        int v = Integer.MAX_VALUE;

        for (int[] action : actions(board)) {
            v = Math.min(v, maxValue(result(board, action)));
        }
        return v;
    }

    public int[] minimax(String[][] board, String turno) {
        if (terminal(board)) return null;

        List<int[]> bestActions = new ArrayList<>();
        int[] bestAction = null;

        if (turno.equals(X)) {
            int bestValue = Integer.MIN_VALUE;
            for (int[] action : actions(board)) {
                int value = minValue(result(board, action));
                if (value > bestValue) {
                    bestActions.clear();
                    bestActions.add(action);
                    bestValue = value;
                } else if (value == bestValue) {
                    bestActions.add(action);
                }
            }
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (int[] action : actions(board)) {
                int value = maxValue(result(board, action));
                if (value < bestValue) {
                    bestActions.clear();
                    bestActions.add(action);
                    bestValue = value;
                } else if (value == bestValue) {
                    bestActions.add(action);
                }
            }
        }

        if (bestActions.isEmpty()) return null;
        Random rand = new Random();
        bestAction = bestActions.get(rand.nextInt(bestActions.size()));
        return bestAction;
    }
}