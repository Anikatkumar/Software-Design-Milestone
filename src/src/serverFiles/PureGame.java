package serverFiles;

import java.util.Arrays;

public class PureGame {
    private int width;
    private int height;
    private int[][] cells; // 2D array representing the Tetris board
    private int[][] currentShape;
    private int[][] nextShape;
    private static PureGame instance;

    public static PureGame getInstance(int width, int height) {
        if (instance == null) {
            instance = new PureGame();
        }
        return instance;
    }

    @Override
    public String toString() {
        return "PureGame{" +
                "width=" + width +
                ", height=" + height +
                ", cells=" + Arrays.deepToString(cells) +
                ", currentShape=" + Arrays.deepToString(currentShape) +
                ", nextShape=" + Arrays.deepToString(nextShape) +
                '}';
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setCells(int[][] cells) {
        this.cells = cells;
    }

    public int[][] getCells() {
        return cells;
    }

    public void setCurrentShape(int[][] currentShape) {
        this.currentShape = currentShape;
    }

    public int[][] getCurrentShape() {
        return currentShape;
    }

    public void setNextShape(int[][] nextShape) {
        this.nextShape = nextShape;
    }

    public int[][] getNextShape() {
        return nextShape;
    }
}


