package ui;

import settings.GameSettings;

import javax.swing.*;
import java.awt.*;

public class InfoBoard extends JPanel {
    GameSettings gameSettings = new GameSettings();
    private String playerNumber = "1";
    private String playerType = "Human";
    private JLabel currentLevelLabel;
    private int linesErased = 0;
    private JLabel linesErasedLabel = new JLabel("Lines Erased: 0", JLabel.CENTER);
    private JLabel scoreLabel = new JLabel("Score: 0", JLabel.CENTER);

    public JPanel createInfoPanel(String playerNumber) {
        this.playerNumber = playerNumber;

        // Read settings from JSON or other source
        gameSettings = new GameSettings().readSettingsFromJsonFile();

        this.playerType = playerNumber.equals("2") ? gameSettings.getPlayerTwoType() : gameSettings.getPlayerOneType();
        int initialLevel = gameSettings.getGameLevel();
        int currentLevel = initialLevel;

        JLabel playerInfoLabel = new JLabel("Game Info (Player " + playerNumber + ")", JLabel.CENTER);
        JLabel playerTypeLabel = new JLabel("Player Type: " + playerType, JLabel.CENTER);
        JLabel initialLevelLabel = new JLabel("Initial Level: " + initialLevel, JLabel.CENTER);
        currentLevelLabel = new JLabel("Current Level: " + currentLevel, JLabel.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1));

        infoPanel.add(playerInfoLabel);
        infoPanel.add(playerTypeLabel);
        infoPanel.add(initialLevelLabel);
        infoPanel.add(currentLevelLabel);
        infoPanel.add(linesErasedLabel);
        infoPanel.add(scoreLabel);

        infoPanel.setPreferredSize(new Dimension(150, 400));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        return infoPanel;
    }

    public void updateLevel(int level) {
        currentLevelLabel.setText("Current Level: " + level);
        System.out.println("(InfoBoard) Current Level-Up: " + level);
        currentLevelLabel.revalidate();
        currentLevelLabel.repaint();
    }

    public void updateLinesErased(int linesErased) {
        linesErasedLabel.setText("Lines Erased: " + linesErased);
        System.out.println("(InfoBoard) Lines Erased: " + linesErased);
        linesErasedLabel.revalidate();
        linesErasedLabel.repaint();
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
        System.out.println("(InfoBoard) Score: " + score);
        scoreLabel.revalidate();
        scoreLabel.repaint();
    }
}
