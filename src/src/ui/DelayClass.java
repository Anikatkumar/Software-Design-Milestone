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
    public boolean flagCheck;

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
//                        System.out.println("pase");
                        pauseLock.wait();  // Wait until the game is resumed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Game logic for moving the block down and other game operations
            Color colorAssigned = gameBoard.createNewBlock();
//            while (gameBoard.moveBlockDown() && !gamePaused) {
            while (gameBoard.moveBlockDown()) {
                try {
                    Thread.sleep(350);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            gameOver = gameBoard.maximumHeightReached();
            if (gameOver) {
                System.out.println("Maximum height reached");
                break;
            }
            gameBoard.mergeBlock(colorAssigned);
            totalScore = gameBoard.clearOutCompletedLines();
            // Update the score if necessary
            // gameScreen.updateScore(totalScore);
        }
    }

    public void pauseGame() {
        synchronized (pauseLock) {
            System.out.println();
            gamePaused = true;
            flagCheck = gamePaused;
        }
    }

    public void resumeGame() {
        synchronized (pauseLock) {
            gamePaused = false;
            flagCheck = gamePaused;
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

    public boolean gamePaused() {
        return flagCheck;
    }
}

//package ui;
//
//import java.awt.*;
//
//public class DelayClass extends Thread {
//    private GameBoard gameBoard;
//    private GameScreen gameScreen;
//    private int totalScore = 0;
//    private boolean gameOver;
//    private final Object lock = new Object();
//    public boolean gameRunning  = true;
//    public boolean gamePaused  = false;
//
//    public DelayClass(GameBoard gameBoard, GameScreen gameScreen) {
//        this.gameBoard = gameBoard;
//        this.gameScreen = gameScreen;
//    }
//
////    public void pauseGame() {
////        try {
////            pause = true;
////            Thread.sleep(1);
////        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
////        }
////    }
//
//    @Override
//    public void run() {
//        while (gameRunning) {
//            Color colorAssigned = gameBoard.createNewBlock();
//            while (gameBoard.moveBlockDown()) {
//                try {
//                    Thread.sleep(350);
//                } catch (InterruptedException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//            gameOver = gameBoard.maximumHeightReached();
//            if (gameOver) {
//                System.out.println("Maximum height reached");
//                break;
//            }
//            gameBoard.mergeBlock(colorAssigned);
//            totalScore = gameBoard.clearOutCompletedLines();
////           gameScreen.updateScore(totalScore);
////           gameScreen.updateScore(584);
//        }
//    }
//
//    public void pauseGame()  {
////        gameRunning = !gameRunning;
//    }
//    public void resumeGame(){
////        Thread.start();
////        gameRunning = !gameRunning;
//    }
//    public boolean isGameRunning() {
//        return gameRunning;
//    }
//}
