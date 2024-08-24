package ui;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class GameBlock {
    private int[][] blockShape;
    private Color blockColor;
    private int[][][] blockShapesRotations;
    private int degreeOfRotation = 0;


    public GameBlock(int[][] blockShape, Color blockColor) {
//        System.out.println(blockShape);
        this.blockShape = blockShape;
        this.blockColor = blockColor;
        transposeShapeMatrixToRotate();
        degreeOfRotation = 0;
    }
    public int[][] getBlockShape() {
        return blockShape;
    }

    public Color getBlockColor() {
        return blockColor;
    }

    public void rotateBlock() {
        degreeOfRotation++;
        if (degreeOfRotation > 3) {
            degreeOfRotation = 0;
        }
        blockShape = blockShapesRotations[degreeOfRotation];
    }

    public void transposeShapeMatrixToRotate(){
        blockShapesRotations = new int[4][][];
        for(int i = 0; i < 4; i++){
            int matrixRows = blockShape[0].length;
            int matrixCols = blockShape.length;
            blockShapesRotations[i] =  new int[matrixRows][matrixCols];
            for(int j = 0; j < matrixRows; j++){
                for(int k = 0; k < matrixCols; k++){
                    blockShapesRotations[i][j][k] = blockShape[matrixCols - k - 1][j];
                }
            }
            blockShape = blockShapesRotations[i];
//            System.out.println(Arrays.toString(blockShapesRotations));
        }
    }

}
