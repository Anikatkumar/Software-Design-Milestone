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
    private DelayClass threadClass;
    private PlayLabel playLabel;

    public GameScreen() {
        System.out.println("GAME SCREEN DISPLAY");
        gameBoard = new GameBoard(20);

        playLabel = new PlayLabel();
        gameKeyboardControls();
        this.add(gameBoard);
        this.add(playLabel);

        threadClass = new DelayClass(gameBoard, this);
        threadClass.start();
//        DelayClass threadClass = new DelayClass(gameBoard, this).start();

        /*
        Passing the following arguments:
        1. frame - to close current frame (game screen) and launch main menu
        2. threadClass - to allow resume game
        */
        backButton = new BackButton(this, threadClass, this);
        this.add(backButton);

        scoreLabel = new JLabel();
    }


    public void showScreen() {
        GameScreen gameScreen = new GameScreen();
        int width = 750;
        int height = 600;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        gameScreen.setLayout(new BorderLayout());
        gameScreen.setBounds(x, y, width, height);
        gameScreen.setVisible(true);
        gameScreen.setTitle("Play");
        add(backButton);
        gameScreen.setDefaultCloseOperation(EXIT_ON_CLOSE);


    }
    private static JPanel getButtonPanel(JFrame frame) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenuScreen().showMainScreen();
                frame.dispose();
            }
        });
        backButton.setBackground(Color.lightGray);
        backButton.setOpaque(true);
        Dimension buttonSize = new Dimension(150, 40);
        backButton.setPreferredSize(buttonSize);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        return buttonPanel;

    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    protected void gameKeyboardControls() {
        InputMap keyInput = this.getRootPane().getInputMap();
        ActionMap keyActionMap = this.getRootPane().getActionMap();

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
                        System.out.println("Game resumed");
                        threadClass.resumeGame();  // Resume the game
                    } else {
                        System.out.println("Game paused");
                        threadClass.pauseGame();  // Pause the game
                    }
                }
            }
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("game paused");
//
//                boolean gameRunning = threadClass.isGameRunning();
//                if(gameRunning) {
//                    threadClass.pauseGame();
//                }else{
//                    threadClass.resumeGame();
//                }
//
//            }
        });

    }

}
