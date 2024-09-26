package ui;

import java.awt.*;

public class DelayClass extends Thread {
    private GameBoard gameBoard;
    private GameScreen gameScreen;
    private int totalScore = 0;
    private boolean gameOver;
    private final Object pauseLock = new Object();
    public boolean gameRunning = true;
    public boolean gamePaused = false;
    public Color colorAssigned;
    public int speedBlock = 100;
    public int level = 1;
    public DelayClass(GameBoard gameBoard, GameScreen gameScreen) {
        this.gameBoard = gameBoard;
        this.gameScreen = gameScreen;
    }

    public void run() {
        while (gameRunning) {
            synchronized (pauseLock) {
                while (gamePaused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (gameScreen.isAIEnabled()) {
                // If AI is enabled, let the AI make the moves
                gameScreen.updateGame();
                try {
                    Thread.sleep(100);  // AI should move blocks faster (adjust as needed)
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                // Control the block movement
                if (colorAssigned == null) {
                    colorAssigned = gameBoard.createNewBlock().getBlockColor();  // Create new block if none exists
                }

                boolean blockMovedDown = gameBoard.moveBlockDown();
                if (!blockMovedDown) {
                    gameBoard.mergeBlock(colorAssigned);  // Merge block if it can no longer move
                    totalScore += gameBoard.clearOutCompletedLines();  // Clear lines and update score

                    gameScreen.updateScore(totalScore);  // Update the score on the screen

                    // Reset color to create a new block in the next loop
                    colorAssigned = null;
                }
            }

            try {
                Thread.sleep(speedBlock);  // Adjust speed based on level
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void pauseGame() {
//        System.out.println("(DelayClass) pauseGame()");
        synchronized (pauseLock) {
            gamePaused = true;
        }
    }

    public void resumeGame() {
//        System.out.println("(DelayClass) resumeGame()");
        synchronized (pauseLock) {
            gamePaused = false;
            pauseLock.notify();  // Resume the game
        }
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void stopGame() {
        gameRunning = false;
        resumeGame();  // Ensure the thread is not stuck waiting
    }

    public boolean gameOver(){
        return gameBoard.maximumHeightReached();
    }
}