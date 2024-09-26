package ui;

import AI.Move;
import AI.TetrisAI;
import settings.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameScreen extends JFrame {
    private JPanel GameBoard;
    private JButton Back;
    private GameBoard gameBoard;
    private BackButton backButton;
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private JLabel pauseLabel;
    private DelayClass threadClass;
    private PlayLabel playLabel;
    GameSettings gameSettings = new GameSettings();

    // AI variable
    private TetrisAI ai;
    private boolean useAI = false;  // Toggle this to switch between AI and player

    public GameScreen() {
        gameSettings = gameSettings.readSettingsFromJsonFile();
        gameBoard = new GameBoard(20);
        ai = new TetrisAI();

        playLabel = new PlayLabel();

        pauseLabel = new JLabel("Press 'P' again to resume the game   ", JLabel.RIGHT);
        pauseLabel.setVisible(false);
        pauseLabel.setFont(new Font("Arial", Font.ITALIC, 10));

        this.setLayout(new BorderLayout());
        this.add(gameBoard);
        this.add(playLabel);

        gameBoard.add(pauseLabel);


        threadClass = new DelayClass(gameBoard, this);
        threadClass.start();

        gameBoard.initializeThread(threadClass);

        backButton = new BackButton(this, threadClass, this);
        this.add(backButton, BorderLayout.SOUTH);

        scoreLabel = new JLabel("Score: 0", JLabel.LEFT);
        levelLabel = new JLabel("Level: 1", JLabel.LEFT);

        add(scoreLabel, BorderLayout.WEST);
        add(levelLabel, BorderLayout.BEFORE_FIRST_LINE);
        scoreLabel.setVisible(true);
        levelLabel.setVisible(true);

        gameBoard.initializeAI();
        gameKeyboardControls();
    }
    public void setAIEnabled(boolean enabled) {
        this.useAI = enabled;
    }

    public boolean isAIEnabled() {
        return this.useAI;
    }

    public void showScreen() {
        int width = 600;
        int height = 600;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        this.setBounds(x, y, width, height);
        this.setVisible(true);
        this.setTitle("Play");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateLevel(int level) {
        levelLabel.setText("Level: " + level);
    }

    protected void gameKeyboardControls() {
        InputMap keyInput = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap keyActionMap = this.getRootPane().getActionMap();

        keyInput.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        keyInput.put(KeyStroke.getKeyStroke("DOWN"), "down");
        keyInput.put(KeyStroke.getKeyStroke("UP"), "up");
        keyInput.put(KeyStroke.getKeyStroke("LEFT"), "left");
        keyInput.put(KeyStroke.getKeyStroke("P"), "pause");
        keyInput.put(KeyStroke.getKeyStroke("A"), "toggleAI");

        keyActionMap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!useAI) {
                    if (gameSettings.isGameSoundsOn()) {
                        GameBlock.playMoveTurnMusic();
                    }
                    gameBoard.moveBlockRight();
                }
            }
        });

        keyActionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!useAI) {
                    if (gameSettings.isGameSoundsOn()) {
                        GameBlock.playMoveTurnMusic();
                    }
                    gameBoard.moveBlockLeft();
                }
            }
        });

        keyActionMap.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!useAI) {
                    if (gameSettings.isGameSoundsOn()) {
                        GameBlock.playMoveTurnMusic();
                    }
                    gameBoard.rotateBlockOnUpKeyPressed();
                }
            }
        });

        keyActionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!useAI) {
                    if (gameSettings.isGameSoundsOn()) {
                        GameBlock.playMoveTurnMusic();
                    }
                    gameBoard.moveBlockDownFast();
                }
            }
        });

        keyActionMap.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (threadClass.gamePaused) {
                    pauseLabel.setVisible(false);
                    threadClass.resumeGame();
                } else {
                    pauseLabel.setVisible(true);
                    threadClass.pauseGame();
                }
            }
        });

        keyActionMap.put("toggleAI", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                useAI = !useAI;
                if (useAI) {
                    System.out.println("AI Control Enabled");
                } else {
                    System.out.println("AI Control Disabled");
                }
            }
        });
    }

    public void updateGame() {
        if (useAI) {
            Move bestMove = ai.findBestMove(gameBoard, gameBoard.getCurrentBlock());

            for (int i = 0; i < bestMove.rotation; i++) {
//                gameBoard.rotateBlockOnUpKeyPressed();
            }

            while (gameBoard.getBlockXGridPosition() < bestMove.column) {
                System.out.println("gameBoard.getBlockXGridPosition(): < " + gameBoard.getBlockXGridPosition() + ":  bestMove.column" + bestMove.column);
                gameBoard.moveBlockRight();
            }
            while (gameBoard.getBlockXGridPosition() > bestMove.column) {
                System.out.println("gameBoard.getBlockXGridPosition(): > " + gameBoard.getBlockXGridPosition() + ":  bestMove.column" + bestMove.column);
                gameBoard.moveBlockLeft();
            }

            gameBoard.moveBlockDownFast();
        }
    }
}
