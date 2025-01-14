package ui;

import blockFactories.BlockFactoryProducer;
import serverFiles.PureGame;
import serverFiles.TetrisClient;
import settings.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static java.lang.Math.abs;

public class GameBoard extends JPanel {
    private final int noOfRows;
    private final int noOfColumns;
    private int blockSize;
    protected GameBlock gameBlock;
    protected GameBlock nextGameBlock;
    int xAxis;
    int yAxis;
    int temp = 0;
    private DelayClass threadClass;
    GameSettings gameSettings = new GameSettings();
    int blockXGridInitialPosition;
    int blockYGridInitialPosition;
    private final Color[][] settledBlocks;
    public int score = 0;
    private int initialLevel;
    private int currentLevel;
    private int linesErased = 0;
    private GameScreen gameScreen;
    Color createdNewBlockWithColor;

    // New
    private InfoBoard infoBoard = new InfoBoard();
    public JPanel infoPanel;
    String playerNum;
    String playerType;

    // Game Block Sequence Pointer
    protected int sequenceID = 0;

    // Tetris Client Request
    protected PureGame pureGame;
    private int[][] settledIntBlocks;
    protected boolean tetrisServer = false;
    private boolean firstRun = true;

    public GameBoard(GameScreen gameScreen, String playerNum) {
        // Read settings from JSON or other source
        gameSettings = gameSettings.readSettingsFromJsonFile();
        this.gameScreen = gameScreen;
        this.playerNum = playerNum;
        this.playerType = this.playerNum.equalsIgnoreCase("1") ? gameSettings.getPlayerOneType() : gameSettings.getPlayerTwoType();
        initialLevel = gameSettings.getGameLevel();
        currentLevel = initialLevel;

        // Tetris Client : Initialize Client
        pureGame = new PureGame();
        pureGame.setWidth(gameScreen.getWidth());
        pureGame.setHeight(gameScreen.getHeight());

        // Initialize number of columns and rows from GameSettings
        this.noOfColumns = gameSettings.getFieldWidth();
        this.noOfRows = gameSettings.getFieldHeight();  // Determine the number of rows from GameSettings
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        // Initialized. Stores the blocks that have settled.
        settledBlocks = new Color[noOfRows][noOfColumns];

        // Add component listener to detect resizing
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Adjust block size based on new panel size, but keep noOfRows from gameSettings
                adjustBoardSize();
            }
        });

        // Creates the Info Board for this Game Board
        infoPanel = infoBoard.createInfoPanel(playerNum);
        createdNewBlockWithColor = createNewBlock();
    }

    /**
     * Adjust block size when the panel is resized but keep the number of rows static according to gameSettings
     */
    private void adjustBoardSize() {
        // Get current width and height of the panel
        int newWidth = getWidth();
        int newHeight = getHeight();

        // Calculate new block size based on the smaller dimension
        blockSize = Math.min(newWidth / noOfColumns, newHeight / noOfRows);

        // Recalculate the preferred size of the panel based on the block size and number of rows/columns
        int preferredWidth = blockSize * noOfColumns;
        int preferredHeight = blockSize * noOfRows;
        this.setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        int bottomBorderWidth = this.getHeight() - preferredHeight;
        this.setBorder(BorderFactory.createMatteBorder(1, 1, bottomBorderWidth > 0 ? bottomBorderWidth : 1, 1, Color.BLACK));

        // Repaint the panel with the updated sizes
        revalidate();  // Ensure the layout is refreshed
        repaint();
    }

    public void setTetrisServerStatus(boolean status) {
        this.tetrisServer = status;
    }

    public void serverMove(int x_position, int rotation_count) {
        if (rotation_count == 0) {
//            System.out.println("No rotation needed.");
        } else {
//            System.out.println("Rotate the piece " + rotation_count + " times.");
            for (int count = 0; count < rotation_count; count++) {
                if (gameSettings.isGameSoundsOn()) {
                    GameBlock.playMoveTurnMusic();
                }
                rotateBlockOnUpKeyPressed();
            }
        }

        //Get the current x-pos of the block and decide left or right
        int newBlock_initialPosition = blockXGridInitialPosition;

        if (newBlock_initialPosition < x_position) {
//            System.out.println("Moving Block to the Right");
            while (newBlock_initialPosition < x_position) {
                if (gameSettings.isGameSoundsOn()) {
                    GameBlock.playMoveTurnMusic();
                }
                moveBlockRight();
                newBlock_initialPosition++;
            }
        }
        if (newBlock_initialPosition > x_position) {
//            System.out.println("Moving Block to the Left");
            while (newBlock_initialPosition > x_position) {
                if (gameSettings.isGameSoundsOn()) {
                    GameBlock.playMoveTurnMusic();
                }
                moveBlockLeft();
                newBlock_initialPosition--;
//                System.out.println("Left");
            }
        }

//        System.out.println("Moving block down.");
        while (true) {
            if (!checkBottom()) {
                break;
            } else {
                moveBlockDownFast();
                try {
                    // Adding a delay of 100 milliseconds (0.1 seconds) for block drop
                    Thread.sleep(100 - (this.currentLevel * 2));
                } catch (InterruptedException e) {
                    // Handle the exception if the sleep is interrupted
                    e.printStackTrace();
                }
            }
        }
    }

    // Convert Color settledBlocks into Int settledIntBlocks
    public int[][] convertBlocksToInt() {
        int rows = settledBlocks.length;
        int columns = settledBlocks[0].length;
        settledIntBlocks = new int[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                // If the color is null, it's a 0 (no block); otherwise, it's a 1 (block present)
                settledIntBlocks[row][col] = (settledBlocks[row][col] != null) ? 1 : 0;
            }
        }
        return settledIntBlocks;
    }

    public void initializeThread(DelayClass thread) {
        this.threadClass = thread;
    }

    public boolean checkBottom() {
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
                rowsErased++;
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
            if (gameSettings.isGameSoundsOn()) {
                GameBlock.playEraseLineMusic();
            }
            updateScoreOnRowsCleared(rowsErased);

            linesErased += rowsErased;
            //System.out.println("Total lines erased: " + linesErased);

            infoBoard.updateLinesErased(linesErased);

            temp += rowsErased;
            System.out.println("Current Level: " + currentLevel + " Temp: " + temp);
            ;
            if (temp >= 10) {
                temp = 0;
                GameBlock.playLevelUpMusic();
                currentLevel++;
                System.out.println(currentLevel);
                infoBoard.updateLevel(currentLevel);
                threadClass.increaseBlockSpeed(currentLevel);
                System.out.println("Current Level: " + currentLevel);

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

        infoBoard.updateScore(score);
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
        gameBlock = BlockFactoryProducer.getRandomBlock().createBlock(); // Use factory to create the block

        gameScreen.gameBlockSequence.add(gameBlock);
        // Using GameBlock Copy Constructor to avoid reference copy from gameBlockSequence
        gameBlock = new GameBlock(gameScreen.gameBlockSequence.get(sequenceID));
        this.sequenceID++;

        nextGameBlock = BlockFactoryProducer.getRandomBlock().createBlock();
        gameScreen.gameBlockSequence.add(nextGameBlock);
        nextGameBlock = new GameBlock(gameScreen.gameBlockSequence.get(sequenceID));

        if (gameSettings.isExtendModeOn() && this.playerType.equalsIgnoreCase("External")) {
            this.pureGame.setNextShape(nextGameBlock.getBlockShape());
        }

        // Position new block to the center of the GameBoard
        blockXGridInitialPosition = (noOfColumns / 2) - 1;
        blockYGridInitialPosition = -gameBlock.getBlockShape().length;

        // Tetris Client
        if (gameSettings.isExtendModeOn() && this.playerType.equalsIgnoreCase("External")) {
            pureGame.setWidth(gameSettings.getFieldWidth());
            pureGame.setHeight(gameSettings.getFieldHeight());
            pureGame.setNextShape(nextGameBlock.getBlockShape());
            pureGame.setCurrentShape(gameBlock.getBlockShape());
            pureGame.setCells(convertBlocksToInt());

            // This condition is to prevent communicating with the server until the Game Board is displayed later.
            if (!this.firstRun) {
                this.firstRun = false;
                return null;
            } else {
                TetrisClient client = new TetrisClient(this);
            }
        }
        return gameBlock.getBlockColor();
    }

    public PureGame getPureGame() {
        return pureGame;
    }

    public void paintBlock(Graphics g) {
        if (gameBlock != null) {
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

            // Tetris Client
            if (gameSettings.isExtendModeOn() && this.playerType.equalsIgnoreCase("External")) {
                nextGameBlock = new GameBlock(gameScreen.gameBlockSequence.get(sequenceID));
                this.pureGame.setWidth(gameSettings.getFieldWidth());
                this.pureGame.setHeight(gameSettings.getFieldHeight());
                this.pureGame.setNextShape(nextGameBlock.getBlockShape());
                this.pureGame.setCurrentShape(gameBlock.getBlockShape());
                this.pureGame.setCells(convertBlocksToInt());
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
        System.out.println("Game Block Rotation");
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