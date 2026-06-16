package com.example.egzamin;

public class SudokuCell {
    private int value;
    private int solution;
    private boolean isFixed;
    private boolean hasError;

    public SudokuCell(int solution, boolean isFixed) {
        this.solution = solution;
        this.isFixed = isFixed;
        this.value = isFixed ? solution : 0;
        this.hasError = false;
    }


    public int getValue() { return value; }

    public void setValue(int value) {
        if (!isFixed) {
            this.value = value;
        }
    }

    public int getSolution() { return solution; }

    public boolean isFixed() { return isFixed; }

    public boolean hasError() { return hasError; }

    public void setError(boolean error) { this.hasError = error; }

    public boolean isEmpty() { return value == 0; }

    public boolean isCorrect() { return value == solution; }

}
