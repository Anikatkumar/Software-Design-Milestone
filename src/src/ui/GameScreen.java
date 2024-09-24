package ui;

import settings.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameScreen extends JFrame {
    private JPanel GameBoard;
    GameSettings gameSettings = new GameSettings();
    private JButton Back;
    private BackButton backButton;
    protected JLabel pauseLabel, pauseLabel2;
    private JLabel musicLabel;

    // Player 1 Settings
    private GameBoard gameBoard;
    protected DelayClass threadClass;

    // Player 2 Settings
    private GameBoard gameBoard2;
    protected DelayClass threadClass2;


    public GameScreen() {
        System.out.println("GAME SCREEN DISPLAY");
        gameSettings = gameSettings.readSettingsFromJsonFile();

        // Pause Label
        this.pauseLabel = new JLabel("Press 'P' again to resume the game   ", JLabel.RIGHT);
        this.pauseLabel.setVisible(false);
        this.pauseLabel.setFont(new Font("Arial", Font.ITALIC, 10));  // Customize font size and style
        // Pause Label 2
        this.pauseLabel2 = new JLabel("Press 'P' again to resume the game   ", JLabel.RIGHT);
        this.pauseLabel2.setVisible(false);
        this.pauseLabel2.setFont(new Font("Arial", Font.ITALIC, 10));  // Customize font size and style

        JPanel titlePanel = createTitlePanel();
        this.setLayout(new BorderLayout());
        this.add(titlePanel, BorderLayout.NORTH);

        if (!gameSettings.isExtendModeOn()) {
            this.onePlayerMode();
        } else {
            this.twoPlayerMode();
        }

        backButton = new BackButton(this);
        this.add(backButton, BorderLayout.SOUTH);

        gameKeyboardControls();

        this.pack();
        this.setVisible(true);
    }

    public void onePlayerMode() {
        System.out.println("(GameScreen) One Player Mode)");
        gameBoard = new GameBoard(this, "1");

        JPanel infoPanel = gameBoard.infoPanel;

        // To display Info Panel and Game Board Panel Side by Side
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(0, 2));
        containerPanel.setBackground(Color.magenta);
        containerPanel.add(infoPanel);

        gameBoard.setPreferredSize(new Dimension(500, getHeight()));

        containerPanel.add(gameBoard);
        centerPanel.add(containerPanel);
        this.add(centerPanel, BorderLayout.CENTER);
        gameBoard.add(pauseLabel);

        threadClass = new DelayClass(gameBoard, this);
        System.out.println("(GameScreen) GameBoard Thread 1 started::" + threadClass.getName());
        threadClass.start();

        gameBoard.initializeThread(threadClass);

        gameKeyboardControls();

        this.pack();
        this.setVisible(true);
    }

    public void twoPlayerMode() {
        System.out.println("(GameScreen) Two Player Mode");
        gameBoard = new GameBoard(this, "1");
        gameBoard.setPreferredSize(new Dimension(500, getHeight()));

        gameBoard2 = new GameBoard(this, "2");
        gameBoard2.setPreferredSize(new Dimension(500, getHeight()));

        JPanel infoPanel = gameBoard.infoPanel;
        JPanel infoPanel2 = gameBoard2.infoPanel;

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(0, 4));
        containerPanel.setBackground(Color.magenta);
        containerPanel.add(infoPanel);
        containerPanel.add(gameBoard);
        containerPanel.add(infoPanel2);
        containerPanel.add(gameBoard2);

        // To display Info Panel and Game Board Panel Side by Side
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.add(containerPanel);

        this.add(centerPanel, BorderLayout.CENTER);
        gameBoard.add(pauseLabel);
        gameBoard2.add(pauseLabel2);

        // Adjust Window Size
        int width = 750;
        int height = 550;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;

        System.out.println("Center Panel Width: " + centerPanel.getHeight());
        this.setBounds(x, y, 1000, centerPanel.getHeight());

        threadClass = new DelayClass(gameBoard, this);
        System.out.println("(GameScreen) GameBoard Thread 1 started::" + threadClass.getName());
        threadClass.start();
        threadClass2 = new DelayClass(gameBoard2, this);
        System.out.println("(GameScreen) GameBoard Thread 2 started::" + threadClass2.getName());
        threadClass2.start();

        gameBoard.initializeThread(threadClass);
        gameBoard2.initializeThread(threadClass2);

        gameKeyboardControls();

        this.pack();
        this.setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel playPanel = new JPanel();
        playPanel.setLayout(new GridLayout(2, 1));

        JLabel playLabel = new JLabel("Tetris Play", JLabel.CENTER);
        playLabel.setOpaque(true);
        playLabel.setForeground(Color.BLACK);
        playLabel.setFont(new Font("SansSerif", Font.BOLD, 30));

        boolean musicStatus = gameSettings.isGameMusicOn();
        boolean soundStatus = gameSettings.isGameSoundsOn();
        musicLabel = new JLabel("Music: " + ((musicStatus) ? "ON" : "OFF") + "  Sound: " + ((soundStatus) ? "ON" : "OFF"), JLabel.CENTER);

        playPanel.add(playLabel);
        playPanel.add(musicLabel);

        playPanel.setPreferredSize(new Dimension(150, 100));
        playPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        return playPanel;
    }

    public void updateMusicLabel() {
        boolean musicStatus = gameSettings.isGameMusicOn();
        boolean soundStatus = gameSettings.isGameSoundsOn();
        musicLabel.setText("Music: " + ((musicStatus) ? "ON" : "OFF") + "  Sound: " + ((soundStatus) ? "ON" : "OFF"));
        musicLabel.revalidate();
        musicLabel.repaint();
    }

    public void showScreen() {
        //GameScreen gameScreen = new GameScreen();
//        int width = 750;
        int width = 700;
        int height = 700;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        this.setBounds(x, y, width, height);
        this.setVisible(true);
        this.setTitle("Play");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected void gameKeyboardControls() {
        InputMap keyInput = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap keyActionMap = this.getRootPane().getActionMap();

        keyInput.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        keyInput.put(KeyStroke.getKeyStroke("DOWN"), "down");
        keyInput.put(KeyStroke.getKeyStroke("UP"), "up");
        keyInput.put(KeyStroke.getKeyStroke("LEFT"), "left");
        keyInput.put(KeyStroke.getKeyStroke("P"), "pause");
        keyInput.put(KeyStroke.getKeyStroke("M"), "music");
        keyInput.put(KeyStroke.getKeyStroke("S"), "sound");

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
                gameBoard.moveBlockDownFast();
            }
        });
        keyActionMap.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (threadClass.gamePaused) {
                    pauseLabel.setVisible(false);
                    pauseLabel2.setVisible(false);
                    System.out.println("(GameScreen) Game Resumed.");
                    threadClass.resumeGame();
                    threadClass2.resumeGame();
                } else {
                    // Set up the label for the message
                    pauseLabel.setVisible(true); // Toggle visibility
                    pauseLabel2.setVisible(true); // Toggle visibility
                    System.out.println("(GameScreen) Game Paused.");
                    threadClass.pauseGame();
                    threadClass2.pauseGame();
                }
            }
        });


        // sound controls

        keyActionMap.put("music", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameSettings.setGameMusicOn(!gameSettings.isGameMusicOn());
                gameSettings.writeSettingsIntoJsonFile(gameSettings);
                if (gameSettings.isGameMusicOn()) {
                    GameBlock.playBackGroundMusic();
                } else {
                    GameBlock.pauseBackGroundMusic();
                }
                updateMusicLabel();
            }
        });

        keyActionMap.put("sound", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameSettings.setGameSoundsOn(!gameSettings.isGameSoundsOn());
                gameSettings.writeSettingsIntoJsonFile(gameSettings);
                if (gameSettings.isGameSoundsOn()) {
                    GameBlock.playMoveTurnMusic();
                } else {
                    GameBlock.pauseGameSound();

                }
                updateMusicLabel();
            }
        });

    }

}
