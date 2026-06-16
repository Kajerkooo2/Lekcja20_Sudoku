package com.example.egzamin;


import java.util.Random;
public class SudokuBoard {
    private int[][] solution;
    private int[][] grid;
    private boolean[][] fixed;

    public SudokuBoard() {
        solution = new int[4][4];
        grid     = new int[4][4];
        fixed    = new boolean[4][4];
        generate();
    }

    private void generate() {
        int[][][] solutions = {
                {{1,2,3,4}, {3,4,1,2}, {2,1,4,3}, {4,3,2,1}},
                {{2,1,4,3}, {4,3,2,1}, {1,2,3,4}, {3,4,1,2}},
                {{3,4,1,2}, {1,2,3,4}, {4,3,2,1}, {2,1,4,3}},
                {{4,3,2,1}, {2,1,4,3}, {3,4,1,2}, {1,2,3,4}}
        };

        Random random = new Random();
        int[][] chosen = solutions[random.nextInt(solutions.length)];

        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++) {
                solution[r][c] = chosen[r][c];
                grid[r][c]     = chosen[r][c];
                fixed[r][c]    = true;
            }

        int ukryte = 0;
        while (ukryte < 7) {
            int r = random.nextInt(4);
            int c = random.nextInt(4);
            if (fixed[r][c]) {
                grid[r][c]  = 0;
                fixed[r][c] = false;
                ukryte++;
            }
        }
    }

    public int getValue(int row, int col)       { return grid[row][col]; }
    public int getSolution(int row, int col)    { return solution[row][col]; }
    public boolean isFixed(int row, int col)    { return fixed[row][col]; }
    public boolean isEmpty(int row, int col)    { return grid[row][col] == 0; }

    public void setValue(int row, int col, int value) {
        if (!fixed[row][col]) grid[row][col] = value;
    }

    public boolean isCorrect(int row, int col) {
        return grid[row][col] == solution[row][col];
    }

    public boolean isComplete() {
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                if (!isCorrect(r, c)) return false;
        return true;
    }

    public boolean hasEmptyCells() {
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                if (!fixed[r][c] && grid[r][c] == 0) return true;
        return false;
    }
}
