package ui;

import java.awt.*;
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

}
