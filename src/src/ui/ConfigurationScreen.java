package ui;

import settings.GameSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurationScreen extends JFrame {

    private static ConfigurationScreen instance; // Singleton instance

    private boolean aiModeOn;
    private int fieldWidth;
    private int fieldHeight;
    private int initialGameLevel;
    private boolean gameMusicOn;
    private boolean gameSoundsOn;
    private boolean extendModeOn;
    int minLevel = 1;
    int maxLevel = 10;
    // Player Mode variables
    private JRadioButton playerOneHuman, playerOneAI, playerOneExternal;
    private JRadioButton playerTwoHuman, playerTwoAI, playerTwoExternal;

    // Private constructor for Singleton pattern
    private ConfigurationScreen() {
        // READING SETTINGS FROM CONFIGURATION FILE
        GameSettings savedGameSettings = new GameSettings();
        savedGameSettings = savedGameSettings.readSettingsFromJsonFile();
        System.out.println("savedGameSettings: " + savedGameSettings);
        aiModeOn = savedGameSettings.getAiModeOn();
        fieldWidth = savedGameSettings.getFieldWidth();
        fieldHeight = savedGameSettings.getFieldHeight();
        initialGameLevel = savedGameSettings.getGameLevel();
        gameMusicOn = savedGameSettings.isGameMusicOn();
        gameSoundsOn = savedGameSettings.isGameSoundsOn();
        extendModeOn = savedGameSettings.isExtendModeOn();

        // Initialize the configuration screen UI components
        initializeUI(savedGameSettings);
    }

    // Public static method to get the singleton instance
    public static synchronized ConfigurationScreen getInstance() {
        if (instance == null) {
            instance = new ConfigurationScreen();  // Create instance if it doesn't exist
        }
        return instance;
    }

    // UI Initialization logic
    private void initializeUI(GameSettings savedGameSettings) {
        JFrame frame = this;  // Use 'this' instead of creating a new JFrame
        frame.setSize(750, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(50, 60, 50, 0));

        JPanel configurationPanel = setupConfigurationPanel(savedGameSettings);
        mainPanel.add(configurationPanel);

        JPanel buttonPanel = getButtonPanel(this);

        JLabel titleLabel = new JLabel("Configurations", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
        titleLabel.setOpaque(true);
        titleLabel.setForeground(Color.BLACK);

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel setupConfigurationPanel(GameSettings savedGameSettings) {
        JPanel configurationPanel = new JPanel();
        configurationPanel.setLayout(new GridLayout(13, 3, 5, 5));  // Updated for more rows and columns

        // Field Width Slider
        JLabel fieldWidthLabel = new JLabel("Field Width (No of cells):");
        JSlider fieldWidthSlider = new JSlider(5, 15, fieldWidth);
        fieldWidthSlider.setMajorTickSpacing(1);
        fieldWidthSlider.setPaintTicks(true);
        fieldWidthSlider.setPaintLabels(true);

        JLabel fieldWidthValueLabel = new JLabel(String.valueOf(fieldWidthSlider.getValue()));
        fieldWidthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                fieldWidth = fieldWidthSlider.getValue();
                fieldWidthValueLabel.setText(String.valueOf(fieldWidthSlider.getValue()));
                saveSettings(fieldWidth, fieldHeight, aiModeOn, extendModeOn, gameMusicOn, gameSoundsOn, initialGameLevel);
            }
        });

        // Field Height Slider
        JLabel fieldHeightLabel = new JLabel("Field Height (No of cells):");
        JSlider fieldHeightSlider = new JSlider(15, 30, fieldHeight);
        fieldHeightSlider.setMajorTickSpacing(1);
        fieldHeightSlider.setPaintTicks(true);
        fieldHeightSlider.setPaintLabels(true);

        JLabel fieldHeightValueLabel = new JLabel(String.valueOf(fieldHeightSlider.getValue()));
        fieldHeightSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                fieldHeight = fieldHeightSlider.getValue();
                fieldHeightValueLabel.setText(String.valueOf(fieldHeightSlider.getValue()));
                saveSettings(fieldWidth, fieldHeight, aiModeOn, extendModeOn, gameMusicOn, gameSoundsOn, initialGameLevel);
            }
        });

        // Game Level Slider
        JLabel gameLevelLabel = new JLabel("Game Level:");
        JSlider gameLevelSlider = new JSlider(1, 10, initialGameLevel);
        gameLevelSlider.setMajorTickSpacing(1);
        gameLevelSlider.setPaintTicks(true);
        gameLevelSlider.setPaintLabels(true);

        JLabel gameLevelValueLabel = new JLabel(String.valueOf(gameLevelSlider.getValue()));
        gameLevelSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                initialGameLevel = gameLevelSlider.getValue();
                gameLevelValueLabel.setText(String.valueOf(gameLevelSlider.getValue()));
                saveSettings(fieldWidth, fieldHeight, aiModeOn, extendModeOn, gameMusicOn, gameSoundsOn, initialGameLevel);
            }
        });

        // Other components
        JCheckBox musicCheckbox = new JCheckBox("Music (On|Off):");
        musicCheckbox.setSelected(savedGameSettings.isGameMusicOn());
        JLabel musicStatusLabel = new JLabel(savedGameSettings.isGameMusicOn() ? "On" : "Off");

        JCheckBox soundEffectCheckbox = new JCheckBox("Sound Effect (On|Off):");
        soundEffectCheckbox.setSelected(savedGameSettings.isGameSoundsOn());
        JLabel soundEffectStatusLabel = new JLabel(savedGameSettings.isGameSoundsOn() ? "On" : "Off");

        JCheckBox aiPlayCheckbox = new JCheckBox("AI Play (On|Off):");
        aiPlayCheckbox.setSelected(savedGameSettings.getAiModeOn());
        JLabel aiPlayStatusLabel = new JLabel(savedGameSettings.getAiModeOn() ? "On" : "Off");
        extendModeOn = true;
        JCheckBox extendModeCheckbox = new JCheckBox("Extend Mode (On|Off):", extendModeOn);

        //JCheckBox extendModeCheckbox = new JCheckBox("Extend Mode (On|Off):");
        //extendModeCheckbox.setSelected(savedGameSettings.isExtendModeOn());
        JLabel extendModeStatusLabel = new JLabel("On");
        //JLabel extendModeStatusLabel = new JLabel(savedGameSettings.isExtendModeOn() ? "On" : "Off");

        // Player One Type
        JLabel playerOneLabel = new JLabel("Player One Type:");
        playerOneHuman = new JRadioButton("Human", true);
        playerOneAI = new JRadioButton("AI");
        playerOneExternal = new JRadioButton("External");

        ButtonGroup playerOneGroup = new ButtonGroup();
        playerOneGroup.add(playerOneHuman);
        playerOneGroup.add(playerOneAI);
        playerOneGroup.add(playerOneExternal);

        JPanel playerOnePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        playerOnePanel.add(playerOneHuman);
        playerOnePanel.add(playerOneAI);
        playerOnePanel.add(playerOneExternal);

        // Player Two Type
        JLabel playerTwoLabel = new JLabel("Player Two Type:");
        playerTwoHuman = new JRadioButton("Human", true);
        playerTwoAI = new JRadioButton("AI");
        playerTwoExternal = new JRadioButton("External");

        ButtonGroup playerTwoGroup = new ButtonGroup();
        playerTwoGroup.add(playerTwoHuman);
        playerTwoGroup.add(playerTwoAI);
        playerTwoGroup.add(playerTwoExternal);

        JPanel playerTwoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        playerTwoPanel.add(playerTwoHuman);
        playerTwoPanel.add(playerTwoAI);
        playerTwoPanel.add(playerTwoExternal);

        // Update Player Two Type based on Extend Mode
        extendModeCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extendModeOn = extendModeCheckbox.isSelected();
                extendModeStatusLabel.setText(extendModeOn ? "On" : "Off");

                // Enable or disable Player Two Type options based on Extend Mode
                playerTwoHuman.setEnabled(extendModeOn);
                playerTwoAI.setEnabled(extendModeOn);
                playerTwoExternal.setEnabled(extendModeOn);

                // Optionally deselect Player Two options when disabled
                if (!extendModeOn) {
                    playerTwoHuman.setSelected(false);
                    playerTwoAI.setSelected(false);
                    playerTwoExternal.setSelected(false);
                }

                saveSettings(fieldWidth, fieldHeight, aiModeOn, extendModeOn, gameMusicOn, gameSoundsOn, initialGameLevel);
            }
        });

        // Add components to the panel (Ensure everything is in line)
        configurationPanel.add(fieldWidthLabel);
        configurationPanel.add(fieldWidthSlider);
        configurationPanel.add(fieldWidthValueLabel);

        configurationPanel.add(fieldHeightLabel);
        configurationPanel.add(fieldHeightSlider);
        configurationPanel.add(fieldHeightValueLabel);

        configurationPanel.add(gameLevelLabel);
        configurationPanel.add(gameLevelSlider);
        configurationPanel.add(gameLevelValueLabel);

        configurationPanel.add(musicCheckbox);
        configurationPanel.add(musicStatusLabel);
        musicCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                musicStatusLabel.setText(musicCheckbox.isSelected() ? "On" : "Off");
                gameMusicOn = musicCheckbox.isSelected();
                saveSettings(fieldWidth, fieldHeight, aiModeOn, extendModeOn, gameMusicOn, gameSoundsOn, initialGameLevel);
            }
        });
        configurationPanel.add(new JLabel());

        configurationPanel.add(soundEffectCheckbox);
        configurationPanel.add(soundEffectStatusLabel);
        soundEffectCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                soundEffectStatusLabel.setText(soundEffectCheckbox.isSelected() ? "On" : "Off");
                gameSoundsOn = soundEffectCheckbox.isSelected();
                saveSettings(fieldWidth, fieldHeight, aiModeOn, extendModeOn, gameMusicOn, gameSoundsOn, initialGameLevel);
            }
        });
        configurationPanel.add(new JLabel());

        configurationPanel.add(aiPlayCheckbox);
        configurationPanel.add(aiPlayStatusLabel);
        configurationPanel.add(aiPlayStatusLabel);
        aiPlayCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aiPlayStatusLabel.setText(aiPlayCheckbox.isSelected() ? "On" : "Off");
                aiModeOn = aiPlayCheckbox.isSelected();
                saveSettings(fieldWidth, fieldHeight, aiModeOn, extendModeOn, gameMusicOn, gameSoundsOn, initialGameLevel);
            }
        });
        configurationPanel.add(new JLabel());

        configurationPanel.add(extendModeCheckbox);
        configurationPanel.add(extendModeStatusLabel);
        extendModeCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                extendModeStatusLabel.setText(extendModeCheckbox.isSelected() ? "On" : "Off");
                extendModeOn = extendModeCheckbox.isSelected();
                saveSettings(fieldWidth, fieldHeight, aiModeOn, extendModeOn, gameMusicOn, gameSoundsOn, initialGameLevel);
            }
        });

        configurationPanel.add(new JLabel());

        configurationPanel.add(playerOneLabel);
        configurationPanel.add(playerOnePanel);
        configurationPanel.add(new JLabel());

        configurationPanel.add(playerTwoLabel);
        configurationPanel.add(playerTwoPanel);
        configurationPanel.add(new JLabel());

        return configurationPanel;
    }

    public void saveSettings(int width, int height, boolean aiMode, boolean extendMode, boolean musicOn, boolean soundOn, int gameLevel) {
        GameSettings newGameSettings = new GameSettings();
        newGameSettings.setFieldWidth(width);
        newGameSettings.setFieldHeight(height);
        newGameSettings.setGameMusicOn(musicOn);
        newGameSettings.setGameSoundsOn(soundOn);
        newGameSettings.setAiModeOn(aiMode);
        newGameSettings.setExtendModeOn(extendMode);
        newGameSettings.setGameLevel(gameLevel);
        newGameSettings.writeSettingsIntoJsonFile(newGameSettings);
        //extendModeOn = false; // Force it to true by default
        System.out.println("New Settings: " + newGameSettings.toString());
    }

    private static JPanel getButtonPanel(JFrame frame) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenuScreen().showMainScreen();
                frame.dispose();
            } // Static call to show the main screen
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
}

