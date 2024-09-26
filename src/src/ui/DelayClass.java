package ui;

import scores.GameScores;
import settings.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class DelayClass extends Thread {
    private GameBoard gameBoard;
    private GameScreen gameScreen;
    private int totalLines = 0;
    private GameSettings gameSettings = new GameSettings();
    private int score = 0;
    private boolean gameOver;
    private final Object pauseLock = new Object();
    public boolean gameRunning = true;
    public boolean gamePaused = false;
    public Color colorAssigned;
    private int speedBlock = 1000;
    public int level = 1;

    public DelayClass(GameBoard gameBoard, GameScreen gameScreen) {
        this.gameBoard = gameBoard;
        this.gameScreen = gameScreen;
        // increase speed of block on level selected in settings
        increaseBlockSpeed(gameSettings.readSettingsFromJsonFile().getGameLevel());
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
                        System.out.println(e.getMessage());
                    }
                }
            }

            // Condition to prevent creation of new block if there is still a current block
            if (colorAssigned == null) {
                colorAssigned = gameBoard.createNewBlock();
//                System.out.println("(DelayClass) New Block Created.");
            }

            // Move the block down until it can no longer move
            boolean blockMovedDown = gameBoard.moveBlockDown();
            if (!blockMovedDown) {
                // If the block can't move down, it has settled
                gameBoard.mergeBlock(colorAssigned);
                totalLines = gameBoard.clearOutCompletedLines();

                //System.out.println(totalLines + ":  total lines erased");
//                totalLines+=100;
                //gameBoard.updateScoreOnRowsCleared(totalLines);

                // level updation logic
//                if((totalScore ==  1000)){
//                    speedBlock -= 200; //increase speed
//                    if(speedBlock < 0){
//                        speedBlock = 100;  // maintain speed
//                    }
//                    level++;
//                    gameScreen.updateLevel(level);
//                }


//                System.out.println("Score: " + totalScore + ", Speed " + speedBlock + ", Level " + level);
                // Check if the game is over
                gameOver = gameBoard.maximumHeightReached();
                if (gameOver) {
                    score = 0;
                    score = gameBoard.score;
                    GameBlock.playGameFinishMusic();
                    // final game score
                    checkIfTopTen(score);

//                    System.out.println("Maximum height reached");
                    break;
                }

                // Now that the current block has settled, create a new block
                // Reset to indicate that a new block is needed
                colorAssigned = null;
            }

            try {
                Thread.sleep(speedBlock); // Control the speed of the block's downward movement
//                Thread.sleep(2); // Control the speed of the block's downward movement
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void increaseBlockSpeed(int gameLevel) {
        speedBlock = 1100 - (gameLevel * 100);
        System.out.println("Level: " + gameLevel + " Speed Block: " + speedBlock);
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

    public void checkIfTopTen(int score) {
        GameScores gameScores = new GameScores();
        String playerName = null;
        GameScores newScore = new GameScores();
        List<GameScores> savedScoresList = gameScores.readScores();
        // sort pre-saved list in descending order
        savedScoresList.sort(Comparator.comparingInt(GameScores::getScore).reversed());
        // if list size is already less than 10 then save it doesn't need to check
        if (savedScoresList.size() < 10) {
            playerName = askPlayerName();
            newScore.setPlayerName(playerName);
            newScore.setScore(score);
            gameScores.writeScoresInJSONFile(newScore);
        }
        // if list size is greater than 10 then check
        else {
            // check if its in top 10
            if (score > savedScoresList.getLast().getScore()) {// remove last saved score first
                System.out.println("Scores are 10 or More: ");
                savedScoresList.removeLast();
                System.out.println("Removed last score ");
                playerName = askPlayerName();
                newScore.setPlayerName(playerName);
                newScore.setScore(score);
                savedScoresList.add(newScore);
                gameScores.writeListOfScoresInJSONFile(savedScoresList);
            } else {
                System.out.println("Score " + score + " is not in top 10.");
            }
        }

        System.out.println("Final Updated: " + savedScoresList);

    }

    public void backButtonExitTriggered() {
        System.out.println("Back Button Exit Triggered.");
        score = gameBoard.score;
        checkIfTopTen(score);
    }

    public static String askPlayerName() {
        String playerName = JOptionPane.showInputDialog(null,
                "Enter your name:",
                "Name",
                JOptionPane.PLAIN_MESSAGE);

        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Anonymous Player";
        }
        return playerName;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void stopGame() {
        gameRunning = false;
        resumeGame();  // Ensure the thread is not stuck waiting
    }

    public boolean gameOver() {
        return gameBoard.maximumHeightReached();
    }
}