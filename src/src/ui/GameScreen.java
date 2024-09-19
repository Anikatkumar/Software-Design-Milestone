package ui;

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
    private JLabel scoreLabel = new JLabel("Score: 0");
    private JLabel currentLevelLabel = new JLabel("Current Level: 1 ");
    private JLabel pauseLabel;
    private JLabel linesErasedLabel = new JLabel("Lines Erased: 0");
    private DelayClass threadClass;
    private PlayLabel playLabel;
    GameSettings gameSettings = new GameSettings();


    public GameScreen() {
//        System.out.println("GAME SCREEN DISPLAY");
        gameSettings = gameSettings.readSettingsFromJsonFile();
        gameBoard = new GameBoard(20,this);
        playLabel = new PlayLabel();    // Play Label in the Middle of the Play Screen
        pauseLabel = new JLabel("Press 'P' again to resume the game   ", JLabel.RIGHT);
        pauseLabel.setVisible(false);
        pauseLabel.setFont(new Font("Arial", Font.ITALIC, 10));  // Customize font size and style

        this.setLayout(new BorderLayout());
        JPanel infoPanel = createInfoPanel();
        this.add(infoPanel, BorderLayout.WEST);
        this.add(gameBoard, BorderLayout.CENTER);
        //gameBoard.setPreferredSize(new Dimension(300, 350));

        //JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, infoPanel, gameBoard);
        //splitPane.setPreferredSize(new Dimension(totalWidth, 390));  // Set total width and height

        // Set the proportion (40% for info panel, 60% for game board)
        //splitPane.setResizeWeight(0.4);

        // Optional: remove the divider
       //splitPane.setDividerSize(0);
       //this.add(splitPane, BorderLayout.CENTER);
        this.add(playLabel);


        //Does not add this label to the frame but to the panel (Game Board)
        gameBoard.add(pauseLabel);


        threadClass = new DelayClass(gameBoard, this);
//        System.out.println("(GameScreen) NEW threadClass started.");
        threadClass.start();

        gameBoard.initializeThread(threadClass);

        backButton = new BackButton(this, threadClass, this);
        this.add(backButton, BorderLayout.SOUTH);

        //scoreLabel = new JLabel("Score: 0", JLabel.LEFT);

        //levelLabel = new JLabel("Level: 1", JLabel.LEFT);

        //add(scoreLabel, BorderLayout.WEST);
        //add(levelLabel, BorderLayout.BEFORE_FIRST_LINE);
        //scoreLabel.setVisible(true);
        //levelLabel.setVisible(true);

        gameKeyboardControls();
        //this.setPreferredSize(new Dimension(300, 350));

        this.pack();
        this.setVisible(true);
    }
    private JPanel createInfoPanel(){
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1));

        JLabel playerInfoLabel = new JLabel("Game Info (Player 1)", JLabel.CENTER);
        JLabel playerTypeLabel = new JLabel("Player Type: Human", JLabel.CENTER);
        JLabel initialLevelLabel = new JLabel("Initial Level: 1", JLabel.CENTER);

        infoPanel.add(playerInfoLabel);
        infoPanel.add(playerTypeLabel);
        infoPanel.add(initialLevelLabel);
        infoPanel.add(currentLevelLabel);
        infoPanel.add(linesErasedLabel);
        infoPanel.add(scoreLabel);

        infoPanel.setPreferredSize(new Dimension(150, 390));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        return infoPanel;
    }

    public void showScreen() {
        //GameScreen gameScreen = new GameScreen();
//        int width = 750;
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
        //System.out.println("Updating score to: " + score);  // Debug output

        scoreLabel.setText("Score: " + score);
        scoreLabel.revalidate();  // Ensure layout is updated
        scoreLabel.repaint();
    }

    public void updateLevel(int level) {
        currentLevelLabel.setText("Current Level: " + level);
        currentLevelLabel.revalidate();
        currentLevelLabel.repaint();
    }
    public void updateLinesErased(int linesErased) {
        linesErasedLabel.setText("Lines Erased: " + linesErased);
        linesErasedLabel.revalidate();
        linesErasedLabel.repaint();
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
                if (gameSettings.isGameSoundsOn()) {
                    GameBlock.playMoveTurnMusic();
                }
                gameBoard.moveBlockRight();
            }
        });
        keyActionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameSettings.isGameSoundsOn()) {
                    GameBlock.playMoveTurnMusic();
                }
                gameBoard.moveBlockLeft();
            }
        });
        keyActionMap.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameSettings.isGameSoundsOn()) {
                    GameBlock.playMoveTurnMusic();
                }
                gameBoard.rotateBlockOnUpKeyPressed();
            }
        });
        keyActionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameSettings.isGameSoundsOn()) {
                    GameBlock.playMoveTurnMusic();
                }
                gameBoard.moveBlockDownFast();
            }
        });
        keyActionMap.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (threadClass.gamePaused) {
                    pauseLabel.setVisible(false);
//                    System.out.println("(GameScreen) Game Resumed.");
                    threadClass.resumeGame();
                } else {
                    // Set up the label for the message
                    pauseLabel.setVisible(true); // Toggle visibility
                    System.out.println("(GameScreen) Game Paused.");
                    threadClass.pauseGame();
                }
            }
        });
    }

}
