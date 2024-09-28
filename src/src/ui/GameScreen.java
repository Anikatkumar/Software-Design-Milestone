package ui;

import settings.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

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
    // Game Block Sequence
    protected List<GameBlock> gameBlockSequence = new ArrayList<>();

    // Facade for Display
    private GameDisplayFacade gameDisplayFacade;

    public GameScreen() {
        System.out.println("GAME SCREEN DISPLAY");
        gameSettings = gameSettings.readSettingsFromJsonFile();
        gameDisplayFacade = new GameDisplayFacade(); // Initialize the facade

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


        if (!gameSettings.isExtendModeOn()) {
            // One Player Mode Screen Settings
            gameDisplayFacade.showScreen(this, 700, 700);
        } else {
            // Two Player Mode Screen Settings
            int screenWidth = ((gameBoard.getWidth() + 99) / 100) * 100; // Round Up to the Nearest Hundreds
            gameDisplayFacade.showScreen(this, gameBoard.getWidth() * 3, 700);
        }

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

        threadClass = new DelayClass(gameBoard, this, "1");
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
        gameBoard.setPreferredSize(new Dimension(390, getHeight()));

        gameBoard2 = new GameBoard(this, "2");
        gameBoard2.setPreferredSize(new Dimension(390, getHeight()));

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

        // Multi Threading
        threadClass = new DelayClass(gameBoard, this, "1");
        System.out.println("(GameScreen) GameBoard Thread 1 started::" + threadClass.getName());
        threadClass.start();
        threadClass2 = new DelayClass(gameBoard2, this, "2");
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

    protected void gameKeyboardControls() {
        InputMap keyInput = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap keyActionMap = this.getRootPane().getActionMap();

        // Player 1 Controls
        keyInput.put(KeyStroke.getKeyStroke("RIGHT"), "P1 right");
        keyInput.put(KeyStroke.getKeyStroke("DOWN"), "P1 down");
        keyInput.put(KeyStroke.getKeyStroke("UP"), "P1 up");
        keyInput.put(KeyStroke.getKeyStroke("LEFT"), "P1 left");

        // Player 2 Controls
        keyInput.put(KeyStroke.getKeyStroke("typed ."), "P2 right");
        keyInput.put(KeyStroke.getKeyStroke("typed ,"), "P2 left");
        keyInput.put(KeyStroke.getKeyStroke("typed l"), "P2 rotate");

        this.setFocusable(true);
        this.requestFocusInWindow();
        keyInput.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "spacePressed");

        if (gameSettings.isExtendModeOn() && !gameSettings.getPlayerOneType().equalsIgnoreCase("External")) {
            // Player 1 Actions
            keyActionMap.put("P1 right", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameSettings.isGameSoundsOn()) {
                        GameBlock.playMoveTurnMusic();
                    }
                    gameBoard.moveBlockRight();
                }
            });
            keyActionMap.put("P1 left", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameSettings.isGameSoundsOn()) {
                        GameBlock.playMoveTurnMusic();
                    }
                    gameBoard.moveBlockLeft();
                }
            });
            keyActionMap.put("P1 up", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameSettings.isGameSoundsOn()) {
                        GameBlock.playMoveTurnMusic();
                    }
                    gameBoard.rotateBlockOnUpKeyPressed();
                }
            });
            keyActionMap.put("P1 down", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameBoard.moveBlockDownFast();
                }
            });
        }

        if (gameSettings.isExtendModeOn() && !gameSettings.getPlayerTwoType().equalsIgnoreCase("External")) {
            // Player 2 Actions
            keyActionMap.put("P2 right", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameBoard2.moveBlockRight();
                }
            });
            keyActionMap.put("P2 left", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameBoard2.moveBlockLeft();
                }
            });
            keyActionMap.put("P2 rotate", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameBoard2.rotateBlockOnUpKeyPressed();
                }
            });

            keyActionMap.put("spacePressed", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameBoard2.moveBlockDownFast();
                }
            });
        }

        // General Game Controls (Pause, Music, Sound)
        addGeneralGameControls(keyInput, keyActionMap);
    }

    private void addGeneralGameControls(InputMap keyInput, ActionMap keyActionMap) {
        keyInput.put(KeyStroke.getKeyStroke("P"), "pause");
        keyInput.put(KeyStroke.getKeyStroke("M"), "music");
        keyInput.put(KeyStroke.getKeyStroke("S"), "sound");

        keyActionMap.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (threadClass.gamePaused) {
                    pauseLabel.setVisible(false);
                    threadClass.resumeGame();
                    System.out.println("(GameScreen) Game Resumed.");

                    // Player 2 Mode
                    if (gameSettings.isExtendModeOn()) {
                        pauseLabel2.setVisible(false);
                        threadClass2.resumeGame();
                    }
                } else {
                    // Set up the label for the message
                    pauseLabel.setVisible(true); // Toggle visibility
                    threadClass.pauseGame();
                    System.out.println("(GameScreen) Game Paused.");

                    // Player 2 Mode
                    if (gameSettings.isExtendModeOn()) {
                        pauseLabel2.setVisible(true); // Toggle visibility
                        threadClass2.pauseGame();
                    }
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
