package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButton extends JButton {
    private JButton backButton = new JButton("Back");

    public BackButton(JFrame frame, DelayClass gameThread, GameScreen gameScreen) {
        int boardHeight = 100;
        int boardWidth = 100;
        this.setBounds(300, 480, boardWidth, boardHeight);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Back");
        Dimension buttonSize = new Dimension(250, 50);
        backButton.setPreferredSize(buttonSize);
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.setForeground(Color.BLACK);
        backButton.setOpaque(true);
        add(backButton);
        this.setVisible(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Back Button clicked.");
                System.out.println("(GameScreen) Game Paused.");
                gameThread.pauseGame();

                // Confirmation Dialog
                // Yes - Close frame and display Main Menu
                // No - Do nothing. Continue with game
                int response = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to stop the current game?",
                        "Stop Game",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (response == JOptionPane.YES_OPTION) {
                    System.out.println("Confirmation Dialog - Yes");
                    new MainMenuScreen().showMainScreen();
                    frame.dispose();
                } else {
                    // Resume the game if "No" is clicked
                    if (gameThread != null && gameThread.isGameRunning()) {
                        System.out.println("Confirmation Dialog - No");
                        gameThread.resumeGame();
                    }
                }
            }
        });
    }
}