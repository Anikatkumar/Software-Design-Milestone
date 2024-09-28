package ui;

import scores.GameScores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;

public class HighScoresScreen extends JFrame {
    private JPanel highScoresPanel; // Moved to class level to update it easily
    private JFrame frame;

    public HighScoresScreen() {
        GameScores gameScores = new GameScores();
        frame = new JFrame("High Scores");
        frame.setSize(750, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Create a panel for the title and the "Clear Scores" button
        JPanel titlePanel = new JPanel(new BorderLayout());

        // Create the title label
        JLabel titleLabel = new JLabel("High Scores", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
        titleLabel.setOpaque(true);
        titleLabel.setForeground(Color.BLACK);

        // Create the "Clear Scores" button
        JButton clearScoresButton = new JButton("Clear Scores");
        clearScoresButton.setBackground(Color.lightGray);
        clearScoresButton.setOpaque(true);
        clearScoresButton.setPreferredSize(new Dimension(150, 40)); // Set button size

        // Add the title label to the center and the button to the right
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(clearScoresButton, BorderLayout.EAST);

        // Add the title panel to the top of the frame
        frame.add(titlePanel, BorderLayout.NORTH);

        // Initialize the high scores panel with dynamic rows
        highScoresPanel = new JPanel(new GridLayout(0, 2)); // Rows adjust dynamically

        // Load the high scores initially
        loadHighScores(gameScores);

        frame.add(new JScrollPane(highScoresPanel), BorderLayout.CENTER); // Add a scroll pane in case of overflow

        // Create back button
        JPanel buttonPanel = getButtonPanel(frame);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add functionality to the "Clear Scores" button
        clearScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameScores.clearScores(); // Call the method to clear scores from scores.json

                highScoresPanel.removeAll(); // Remove existing components
                loadHighScores(gameScores); // Load empty scores
                JOptionPane.showMessageDialog(frame, "All high scores have been cleared.");

                highScoresPanel.revalidate(); // Refresh the panel
                highScoresPanel.repaint(); // Repaint the panel
            }
        });

        frame.setVisible(true);
    }

    private void loadHighScores(GameScores gameScores) {
        highScoresPanel.removeAll(); // Clear all previous components

        JLabel nameHeader = new JLabel("Name", JLabel.CENTER);
        nameHeader.setFont(new Font("SansSerif", Font.BOLD, 20));
        nameHeader.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nameHeader.setBackground(Color.GRAY);
        nameHeader.setForeground(Color.BLACK);
        nameHeader.setOpaque(true);

        JLabel scoreHeader = new JLabel("Score", JLabel.CENTER);
        scoreHeader.setFont(new Font("SansSerif", Font.BOLD, 20));
        scoreHeader.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scoreHeader.setBackground(Color.GRAY);
        scoreHeader.setForeground(Color.BLACK);
        scoreHeader.setOpaque(true);

        highScoresPanel.add(nameHeader);
        highScoresPanel.add(scoreHeader);

        List<GameScores> gameScoresPlayersInfo = gameScores.readScores();
        gameScoresPlayersInfo.sort(Comparator.comparingInt(GameScores::getScore).reversed());

        int maxRows = 10; // Ensure we fill at least 10 rows

        for (int i = 0; i < maxRows; i++) {
            if (i < gameScoresPlayersInfo.size()) {
                // Populate the panel with the scores
                GameScores gameScore = gameScoresPlayersInfo.get(i);
                int score = gameScore.getScore();
                String name = gameScore.getPlayerName();

                JLabel nameLabel = new JLabel(name, JLabel.CENTER);
                nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                nameLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                JLabel scoreLabel = new JLabel(String.valueOf(score), JLabel.CENTER);
                scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                scoreLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                highScoresPanel.add(nameLabel);
                highScoresPanel.add(scoreLabel);
            } else {
                // Fill with empty labels if fewer than maxRows entries
                JLabel emptyNameLabel = new JLabel("----", JLabel.CENTER);
                emptyNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                emptyNameLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                JLabel emptyScoreLabel = new JLabel("----", JLabel.CENTER);
                emptyScoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                emptyScoreLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                highScoresPanel.add(emptyNameLabel);
                highScoresPanel.add(emptyScoreLabel);
            }
        }

        // Revalidate and repaint the panel to reflect new data
        highScoresPanel.revalidate();
        highScoresPanel.repaint();
    }

    private static JPanel getButtonPanel(JFrame frame) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new MainMenuScreen().showMainScreen();
            frame.dispose();
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
