package ui;

import settings.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.lang.Math.abs;

public class GameBoard extends JPanel {
    private final int noOfRows;
    private final int noOfColumns;
    private final int blockSize;
    private GameBlock gameBlock;
    private JLabel playLabel = new JLabel("Play");
    int xAxis;
    int yAxis;
    private DelayClass threadClass;
    GameSettings gameSettings = new GameSettings();
    int blockXGridInitialPosition;
    int blockYGridInitialPosition;
    private final Color[][] settledBlocks;
    private Color[] blockColors = {Color.CYAN, Color.GREEN, Color.ORANGE, Color.yellow, Color.red, Color.GRAY, Color.pink};
    private Color newBlockColorSelectedAtRandom;
    Color createdNewBlockWithColor;
    private int score=0;
    private int currentLevel = 1;
    private int linesErased = 0;
    private GameScreen gameScreen;

    public int[][][] shapes = {
            {{1, 0}, {1, 0}, {1, 1}},   // L
            {{1, 1, 1}},                // Straight Line
            {{1, 1, 0}, {0, 1, 1}},     // Z
            {{1, 1, 1}, {0, 1, 0}},     // T
            {{1, 1}, {1, 1}},           // Box
            {{0, 1}, {0, 1}, {1, 1}},   // Reverse L
            {{0, 1, 1}, {1, 1, 0}}      // Reverse Z
    };

    public String[] shapeNames = {
            "L",                // L
            "Straight Line",    // Straight Line
            "Z",                // Z
            "T",                // T
            "Box",              // Box
            "Reverse L",        // Reverse L
            "Reverse Z"         // Reverse Z
    };

    public int[][] currentShape;


    public GameBoard(int noOfColumns, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        gameSettings = gameSettings.readSettingsFromJsonFile();
        this.noOfColumns = noOfColumns;
        int boardHeight = 390;
        int boardWidth = 300;
        this.setBounds(150, 60, boardWidth, boardHeight);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        blockSize = (boardWidth / noOfColumns);
        noOfRows = boardHeight / blockSize;
        settledBlocks = new Color[noOfRows][noOfColumns];
        createdNewBlockWithColor = createNewBlock();
//        System.out.println("(Game Board) New Block Created. ");
    }

    public void initializeThread(DelayClass thread) {
        this.threadClass = thread;

        if(gameSettings.isGameMusicOn())
        // music playing background one
        {
            GameBlock.playBackGroundMusic();
        }
    }


    protected boolean checkBottom() {
        if (blockYGridInitialPosition + gameBlock.getBlockShape().length == noOfRows) {
            return false;
        }
        int[][] currentMovingBlock = gameBlock.getBlockShape();
        int currentBlockWidth = gameBlock.getBlockShape()[0].length;
        int currentBlockHeight = gameBlock.getBlockShape().length;
        for (int k = 0; k < currentBlockWidth; k++) {
            for (int l = currentBlockHeight - 1; l >= 0; l--) {
                if (currentMovingBlock[l][k] != 0) {
                    int newXAxis = k + blockXGridInitialPosition;
                    int newYAxis = l + blockYGridInitialPosition + 1;
                    if (newYAxis < 0) {
                        break;
                    }
                    if (settledBlocks[newYAxis][newXAxis] != null) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }


    public int clearOutCompletedLines() {
        boolean flag;
        int rowsErased = 0;
        for (int i = noOfRows - 1; i >= 0; i--) {
            flag = true;
            for (int j = 0; j < noOfColumns; j++) {
                if (settledBlocks[i][j] == null) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                rowsErased ++;
                clearOneCompletedLine(i);
                moveBackgroundLinesDown(i);
                clearOneCompletedLine(0);
                //System.out.println("Rows Erased: " + rowsErased + ", i: " + i) ;

                i++;
                //System.out.println("Rows Erased: " + rowsErased + ", i: " + i) ;

                repaint();
            }
        }
        //System.out.println("Rows erased in this batch: " + rowsErased);

        if (rowsErased > 0) {
            // line erase sound
            if(gameSettings.isGameSoundsOn()) {
                GameBlock.playEraseLineMusic();
            }
            updateScoreOnRowsCleared(rowsErased);

            linesErased += rowsErased;
            //System.out.println("Total lines erased: " + linesErased);

            gameScreen.updateLinesErased(linesErased);


            if(linesErased %10 == 0){
                currentLevel++;
                gameScreen.updateLevel(currentLevel);
                System.out.println("Level up! Current level: " + currentLevel);

            }
        }
        return rowsErased;
    }


    private void clearOneCompletedLine(int rowNumber) {
        for (int k = 0; k < noOfColumns; k++) {
            settledBlocks[rowNumber][k] = null; // clear line
        }
    }
    void updateScoreOnRowsCleared(int rowsErased) {
        if (rowsErased == 1) {
            score += 100;
        } else if (rowsErased == 2) {
            score += 300;
        } else if (rowsErased == 3) {
            score += 600;
        } else if (rowsErased == 4) {
            score += 1000;
        }
        //System.out.println("Updated score: " + score);

        gameScreen.updateScore(score);
    }

    private void moveBackgroundLinesDown(int rowNumber) {
        for (int rowOfBlock = rowNumber; rowOfBlock > 0; rowOfBlock--) {
            if (noOfColumns >= 0)
                System.arraycopy(settledBlocks[rowOfBlock - 1], 0, settledBlocks[rowOfBlock], 0, noOfColumns);
//            for (int columnOfBlock = 0; columnOfBlock < noOfColumns; columnOfBlock++) {
//                settledBlocks[rowOfBlock][columnOfBlock] = settledBlocks[rowOfBlock - 1][columnOfBlock];
//            }
        }
    }


    public Color createNewBlock() {
        Random r = new Random();
        int randomNumber = r.nextInt(shapes.length);

        currentShape = shapes[randomNumber];
        newBlockColorSelectedAtRandom = blockColors[r.nextInt(blockColors.length)];
//        System.out.println("New Shape: " + newBlockColorSelectedAtRandom.toString() + " " + this.shapeNames[randomNumber]);

        gameBlock = new GameBlock(currentShape, newBlockColorSelectedAtRandom);
        blockXGridInitialPosition = 9;
        blockYGridInitialPosition = -gameBlock.getBlockShape().length;
        return newBlockColorSelectedAtRandom;
    }


    public void paintBlock(Graphics g) {
        int[][] drawingShape = gameBlock.getBlockShape();
        for (int i = 0; i < gameBlock.getBlockShape().length; i++) {
            for (int j = 0; j < gameBlock.getBlockShape()[0].length; j++) {
                if (drawingShape[i][j] == 1) {
                    xAxis = (blockXGridInitialPosition + j) * blockSize;
                    yAxis = (blockYGridInitialPosition + i) * blockSize;
//                    System.out.println("Painting Bloc: " + gameBlock.getBlockColor().toString());
                    g.setColor(gameBlock.getBlockColor());
//                    g.setColor(newBlockColorSelectedAtRandom);
                    g.fillRect(xAxis, yAxis, blockSize, blockSize);
                    g.setColor(Color.black);
                    g.drawRect(xAxis, yAxis, blockSize, blockSize);
                }
            }

        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        fillInTheLastBlock(g);
        paintBlock(g);
    }


    /**
     * Fill color to the last settled block on the screen
     */
    private void fillInTheLastBlock(Graphics g) {
//        System.out.println("Filling block");
        Color color;
        for (int i = 0; i < noOfRows; i++) {
            for (int j = 0; j < noOfColumns; j++) {
                color = settledBlocks[i][j];
                if (color != null) {
                    int horizontalPosition = j * blockSize;
                    int verticalPosition = i * blockSize;
                    g.setColor(color);
                    g.fillRect(horizontalPosition, verticalPosition, blockSize, blockSize);
                    g.setColor(Color.black);
                    g.drawRect(horizontalPosition, verticalPosition, blockSize, blockSize);

                }
            }
        }
    }


    /*
     * merge pre spawned blocks to the game board
     * */
    public void mergeBlock(Color color) {
//        System.out.println("MERGING: ");
        int[][] blockShape = gameBlock.getBlockShape();
        int heightOfTheBlock = gameBlock.getBlockShape().length;
        int widthOfTheBlock = gameBlock.getBlockShape()[0].length;
        int horizontalPosition = blockXGridInitialPosition;
        int verticalPosition = blockYGridInitialPosition;
        System.out.println("V: " + verticalPosition + " : H: " + horizontalPosition);
        for (int i = 0; i < heightOfTheBlock; i++) {
            for (int j = 0; j < widthOfTheBlock; j++) {
                if (blockShape[i][j] == 1) {
                    try {
//                        System.out.println("Settling Bloc: " + gameBlock.getBlockColor().toString());
                        settledBlocks[i + abs(verticalPosition)][j + abs(horizontalPosition)] = gameBlock.getBlockColor();
//                        settledBlocks[i + abs(verticalPosition)][j + abs(horizontalPosition)] = color;
                    } catch (Exception e) {
                        System.out.println("Exception: " + e.getMessage());
                    }
                }
            }
        }
    }


    public boolean maximumHeightReached() {
        if (blockYGridInitialPosition < 0) {
            gameBlock = null;
            return true;
        }
        return false;
    }


    public boolean moveBlockDown() {
        // Check if the game is paused before moving the block
        if (threadClass != null && threadClass.gamePaused) {
            return false;
        }

        if (!checkBottom()) {
            return false;
        }
        blockYGridInitialPosition++;
        repaint();
        return true;
    }


    public void moveBlockLeft() {
        if (gameBlock == null) {
            return;
        }

        // Check if the game is paused before moving the block
        if (threadClass != null && threadClass.gamePaused) {
            return;
        }

        if (!checkLeftWallCollision()) {
            return;
        }
        blockXGridInitialPosition--;
        repaint();
    }


    public void moveBlockRight() {
        if (gameBlock == null) {
            return;
        }
        // Check if the game is paused before moving the block
        if (threadClass != null && threadClass.gamePaused) {
            return;
        }
        if (!checkRightWallCollision()) {
            return;
        }
        blockXGridInitialPosition++;
        repaint();
    }


    public void moveBlockDownFast() {
        if (gameBlock == null) {
            return;
        }
        // Check if the game is paused before moving the block
        if (threadClass != null && threadClass.gamePaused) {
            return;
        }
        if (!checkBottom()) {
//            System.out.println("Reached Bottom");
        } else {
            blockYGridInitialPosition++;
            repaint();
        }
    }


    public void rotateBlockOnUpKeyPressed() {
        if (gameBlock == null) {
            return;
        }
        // Check if the game is paused before moving the block
        if (threadClass != null && threadClass.gamePaused) {
            return;
        }
        gameBlock.rotateBlock();
        if (blockXGridInitialPosition < 0) blockXGridInitialPosition = 0;
        if (blockYGridInitialPosition + gameBlock.getBlockShape().length >= noOfRows) {
            blockYGridInitialPosition = noOfRows - gameBlock.getBlockShape().length;
        }
        repaint();
    }


    private boolean checkRightWallCollision() {
        if ((blockXGridInitialPosition + gameBlock.getBlockShape()[0].length) == noOfColumns) {
            return false;
        }
        int[][] currentMovingBlock = gameBlock.getBlockShape();
        int currentBlockWidth = gameBlock.getBlockShape()[0].length;
        int currentBlockHeight = gameBlock.getBlockShape().length;
        for (int k = 0; k < currentBlockHeight; k++) {
            for (int l = currentBlockWidth - 1; l >= 0; l--) {
                if (currentMovingBlock[k][l] != 0) {
                    int newXAxis = l + blockXGridInitialPosition + 1;
                    int newYAxis = k + blockYGridInitialPosition;
                    if (newYAxis < 0) {
                        break;
                    }
                    if (settledBlocks[newYAxis][newXAxis] != null) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }


    /**
     * X coordinate is starting from X value, and hence since user is pressing
     * left key and X axis variable is getting subtracted on each click
     **/
    private boolean checkLeftWallCollision() {
        if (blockXGridInitialPosition == 0) {
            return false;
        }
        int[][] currentMovingBlock = gameBlock.getBlockShape();
        int currentBlockWidth = gameBlock.getBlockShape()[0].length;
        int currentBlockHeight = gameBlock.getBlockShape().length;
        for (int k = 0; k < currentBlockHeight; k++) {
            for (int l = 0; l < currentBlockWidth; l++) {
                if (currentMovingBlock[k][l] != 0) {
                    int newXAxis = l + blockXGridInitialPosition - 1;
                    int newYAxis = k + blockYGridInitialPosition;
                    if (newYAxis < 0) {
                        break;
                    }
                    if (settledBlocks[newYAxis][newXAxis] != null) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }
}