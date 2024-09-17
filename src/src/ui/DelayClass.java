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
    public int speedBlock = 1000;
    public int level = 1;
    public DelayClass(GameBoard gameBoard, GameScreen gameScreen) {
        this.gameBoard = gameBoard;
        this.gameScreen = gameScreen;
    }

    @Override
    public void run() {
        while (gameRunning) {
            synchronized (pauseLock) {
                while (gamePaused) {
                    try {
//                        System.out.println("(DelayClass) Pause");
                        pauseLock.wait();  // Wait until the game is resumed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Condition to prevent creation of new block if there is still a current block
            if (colorAssigned==null) {
                colorAssigned = gameBoard.createNewBlock();
//                System.out.println("(DelayClass) New Block Created.");
            }

            // Move the block down until it can no longer move
            boolean blockMovedDown = gameBoard.moveBlockDown();
            if (!blockMovedDown) {
                // If the block can't move down, it has settled
                gameBoard.mergeBlock(colorAssigned);
                totalScore += gameBoard.clearOutCompletedLines();

                // total score received here is number of lines removed so modifying code now.
                totalScore+=100;

                gameScreen.updateScore(totalScore);

                // level updation logic
                if((totalScore / 4) > level){
                    speedBlock -= 200; //increase speed
                    if(speedBlock < 0){
                        speedBlock = 100;  // maintain speed
                    }
                    level = totalScore/4;
                    gameScreen.updateLevel(level);
                }


                System.out.println("Score: " + totalScore + ", Speed " + speedBlock + ", Level " + level);
                // Check if the game is over
                gameOver = gameBoard.maximumHeightReached();
                if (gameOver) {
                    GameBlock.playGameFinishMusic();
//                    System.out.println("Maximum height reached");
                    break;
                }

                // Now that the current block has settled, create a new block
                // Reset to indicate that a new block is needed
                colorAssigned = null;
            }

            try {
                Thread.sleep(speedBlock); // Control the speed of the block's downward movement
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