package ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class GameBlock {
    private int[][] blockShape;
    private Color blockColor;
    private int[][][] blockShapesRotations;
    private int degreeOfRotation = 0;
    private static SoundPlayer soundPlayer = new SoundPlayer();

    public GameBlock(int[][] blockShape, Color blockColor) {
        this.blockShape = blockShape;
        this.blockColor = blockColor;
        transposeShapeMatrixToRotate();
        degreeOfRotation = 0;
    }

    public List<Point> getShapeAsPoints() {
        List<Point> points = new ArrayList<>();

        for (int y = 0; y < blockShape.length; y++) {
            for (int x = 0; x < blockShape[y].length; x++) {
                if (blockShape[y][x] == 1) {  // If block is filled
                    points.add(new Point(x, y));  // Add it as a Point (x, y)
                }
            }
        }

        return points;
    }

    public static void playBackGroundMusic(){
        soundPlayer.playBackgroundMusic();
    }
    public static void playMoveTurnMusic(){
        soundPlayer.playMoveTurnMusic();
    }
    public static void playEraseLineMusic(){

        soundPlayer.playEraseLineMusic();
    }
    public static void playLevelUpMusic(){
        soundPlayer.playLevelUpMusic();
    }
    public static void playGameFinishMusic(){
        soundPlayer.playGameFinishMusic();
    }
    public static void pauseBackGroundMusic(){
        soundPlayer.pauseBackgroundMusic();
    }

    public static void pauseGameSound(){
        soundPlayer.pauseGameSounds();
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
    public GameBlock clone() {
        // Create a new GameBlock object with the same shape and color
        GameBlock clonedBlock = new GameBlock(copyShape(blockShape), blockColor);

        // Copy the rotation state and other data
        clonedBlock.degreeOfRotation = this.degreeOfRotation;
        clonedBlock.blockShapesRotations = copyAllRotations(this.blockShapesRotations);

        return clonedBlock;
    }
    private int[][] copyShape(int[][] originalShape) {
        int[][] newShape = new int[originalShape.length][originalShape[0].length];
        for (int i = 0; i < originalShape.length; i++) {
            System.arraycopy(originalShape[i], 0, newShape[i], 0, originalShape[i].length);
        }
        return newShape;
    }

    // Helper method to copy the 3D rotation array
    private int[][][] copyAllRotations(int[][][] originalRotations) {
        int[][][] newRotations = new int[originalRotations.length][][];
        for (int i = 0; i < originalRotations.length; i++) {
            newRotations[i] = copyShape(originalRotations[i]);
        }
        return newRotations;
    }
}
