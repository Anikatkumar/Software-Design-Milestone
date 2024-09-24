package ui;

import scores.GameScores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;

public class HighScoresScreen extends JFrame {
    public HighScoresScreen() {
        GameScores gameScores = new GameScores();
        JFrame frame = new JFrame("High Scores");
        frame.setSize(750,550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        //frame.setLayout(new BorderLayout());

        JPanel highScoresPanel = new JPanel(new GridLayout(11, 2));

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
        for (GameScores gameScore : gameScoresPlayersInfo) {
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

        }

//        for (int i = 0; i < name.length; i++) {
//            JLabel nameLabel = new JLabel(name[i], JLabel.CENTER);
//            nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
//            nameLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//
//            JLabel scoreLabel = new JLabel(score[i], JLabel.CENTER);
//            scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
//            scoreLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//
//            highScoresPanel.add(nameLabel);
//            highScoresPanel.add(scoreLabel);
//        }

        frame.add(highScoresPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainMenuScreen().showMainScreen();
                frame.dispose();
            }
        });
        backButton.setBackground(Color.lightGray);
        backButton.setOpaque(true);

        JPanel buttonPanel = getButtonPanel(frame);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        //
        JButton clearScoresButton = new JButton("Clear Scores");
        clearScoresButton.setBackground(Color.lightGray);
        clearScoresButton.setOpaque(true);

        //
        JLabel titleLabel = new JLabel("High Scores", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
        titleLabel.setOpaque(true);
        titleLabel.setForeground(Color.BLACK);

        frame.add(titleLabel, BorderLayout.NORTH);
        //
        frame.add(clearScoresButton, BorderLayout.EAST) ;
        //
        frame.setVisible(true);
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
        Dimension buttonSize = new Dimension(150,40);
        backButton.setPreferredSize(buttonSize);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        return buttonPanel;
    }
}
