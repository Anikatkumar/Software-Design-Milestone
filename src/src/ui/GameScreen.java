package ui;

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
    private JLabel pauseLabel;
    private DelayClass threadClass;
    private PlayLabel playLabel;


    public GameScreen() {
        System.out.println("GAME SCREEN DISPLAY");

        gameBoard = new GameBoard(20);
        playLabel = new PlayLabel();    // Play Label in the Middle of the Play Screen

        pauseLabel = new JLabel("Press 'P' again to resume the game   ", JLabel.RIGHT);
        pauseLabel.setVisible(false);
        pauseLabel.setFont(new Font("Arial", Font.ITALIC, 10));  // Customize font size and style

        this.setLayout(new BorderLayout());
        this.add(gameBoard);
        this.add(playLabel);

        //Does not add this label to the frame but to the panel (Game Board)
        gameBoard.add(pauseLabel);

        threadClass = new DelayClass(gameBoard, this);
        System.out.println("(GameScreen) NEW threadClass started.");
        threadClass.start();

        gameBoard.initializeThread(threadClass);

        backButton = new BackButton(this, threadClass, this);
        this.add(backButton, BorderLayout.SOUTH);

        scoreLabel = new JLabel("Score", JLabel.LEFT);
        add(scoreLabel, BorderLayout.WEST);
        scoreLabel.setVisible(true);

        gameKeyboardControls();
    }


    public void showScreen() {
        //GameScreen gameScreen = new GameScreen();
        int width = 750;
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


    protected void gameKeyboardControls() {
        InputMap keyInput = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap keyActionMap = this.getRootPane().getActionMap();

        keyInput.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        keyInput.put(KeyStroke.getKeyStroke("DOWN"), "down");
        keyInput.put(KeyStroke.getKeyStroke("UP"), "up");
        keyInput.put(KeyStroke.getKeyStroke("LEFT"), "left");
        keyInput.put(KeyStroke.getKeyStroke("P"), "pause");

        keyActionMap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameBoard.moveBlockRight();
            }
        });
        keyActionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameBoard.moveBlockLeft();
            }
        });
        keyActionMap.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameBoard.rotateBlockOnUpKeyPressed();
            }
        });
        keyActionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameBoard.moveBlockDownFast();
            }
        });
        keyActionMap.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (threadClass.gamePaused) {
                    pauseLabel.setVisible(false);
                    System.out.println("(GameScreen) Game Resumed.");
                    threadClass.resumeGame();
                } else {
                    // Set up the label for the message
                    pauseLabel.setVisible(true); // Toggle visibility
                    System.out.println("(GameScreen) Game Paused.");
                    threadClass.pauseGame();
                }
            }
        });
        /*
        keyInput.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        keyInput.put(KeyStroke.getKeyStroke("DOWN"), "down");
        keyInput.put(KeyStroke.getKeyStroke("UP"), "up");
        keyInput.put(KeyStroke.getKeyStroke("LEFT"), "left");
        keyInput.put(KeyStroke.getKeyStroke("P"), "pause");

        keyActionMap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("= Right Key Pressed =");
                gameBoard.moveBlockRight();
            }
        });
        keyActionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("= Left Key Pressed =");
                gameBoard.moveBlockLeft();

            }
        });
        keyActionMap.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("= Up Key Pressed =");
                gameBoard.rotateBlockOnUpKeyPressed();
            }
        });
        keyActionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("= Down Key Pressed =");
                gameBoard.moveBlockDownFast();
            }
        });
        keyActionMap.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (threadClass.isGameRunning()) {
                    if (threadClass.gamePaused) {
                        System.out.println("(Game Screen) Resume Game");
                        threadClass.resumeGame();  // Resume the Game
                    } else {
                        System.out.println("(Game Screen) Pause Game");
                        threadClass.pauseGame();  // Pause the Game
                    }
                }
            }
        });
        */
    }

}
