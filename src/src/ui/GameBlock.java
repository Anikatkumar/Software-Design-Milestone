package ui;

import java.awt.*;

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

    // Copy constructor
    public GameBlock(GameBlock original) {
        // Deep copy of the blockShape array
        int rows = original.blockShape.length;
        int cols = original.blockShape[0].length;
        this.blockShape = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(original.blockShape[i], 0, this.blockShape[i], 0, cols);
        }

        // Copy blockColor (Color is immutable, so a direct copy is fine)
        this.blockColor = original.blockColor;

        // Deep copy of the blockShapesRotations array (if it has been initialized)
        if (original.blockShapesRotations != null) {
            this.blockShapesRotations = new int[original.blockShapesRotations.length][][];
            for (int i = 0; i < original.blockShapesRotations.length; i++) {
                int[][] rotation = original.blockShapesRotations[i];
                this.blockShapesRotations[i] = new int[rotation.length][rotation[0].length];
                for (int j = 0; j < rotation.length; j++) {
                    System.arraycopy(rotation[j], 0, this.blockShapesRotations[i][j], 0, rotation[j].length);
                }
            }
        }

        // Copy the degree of rotation
        this.degreeOfRotation = original.degreeOfRotation;

        // Transpose shape matrix to rotate (if needed)
        transposeShapeMatrixToRotate();
    }

    public static void playBackGroundMusic() {
        soundPlayer.playBackgroundMusic();
    }

    public static void playMoveTurnMusic() {
        soundPlayer.playMoveTurnMusic();
    }

    public static void playEraseLineMusic() {

        soundPlayer.playEraseLineMusic();
    }

    public static void playLevelUpMusic() {
        soundPlayer.playLevelUpMusic();
    }

    public static void playGameFinishMusic() {
        soundPlayer.playGameFinishMusic();
    }

    public static void pauseBackGroundMusic() {
        soundPlayer.pauseBackgroundMusic();
    }

    public static void pauseGameSound() {
        soundPlayer.pauseGameSounds();
    }


    protected int[][] getBlockShape() {
        return blockShape;
    }

    protected Color getBlockColor() {
        return blockColor;
    }

    protected void rotateBlock() {
        degreeOfRotation++;
        if (degreeOfRotation > 3) {
            degreeOfRotation = 0;
        }
        blockShape = blockShapesRotations[degreeOfRotation];
    }

    protected void transposeShapeMatrixToRotate() {
        blockShapesRotations = new int[4][][];
        for (int i = 0; i < 4; i++) {
            int matrixRows = blockShape[0].length;
            int matrixCols = blockShape.length;
            blockShapesRotations[i] = new int[matrixRows][matrixCols];
            for (int j = 0; j < matrixRows; j++) {
                for (int k = 0; k < matrixCols; k++) {
                    blockShapesRotations[i][j][k] = blockShape[matrixCols - k - 1][j];
                }
            }
            blockShape = blockShapesRotations[i];
//            System.out.println(Arrays.toString(blockShapesRotations));
        }
    }

}
