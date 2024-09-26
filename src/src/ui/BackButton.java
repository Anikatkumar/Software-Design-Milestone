package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButton extends JButton {
    private JButton backButton = new JButton("Back");

    public BackButton(GameScreen gameScreen) {
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
        boolean extendedModeOn = gameScreen.gameSettings.isExtendModeOn();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Back Button clicked.");

                gameScreen.pauseLabel.setVisible(true);
                gameScreen.threadClass.pauseGame();

                if (extendedModeOn) {
                    gameScreen.pauseLabel2.setVisible(true);
                    gameScreen.threadClass2.pauseGame();
                }

                // Confirmation Dialog
                // Yes - Close frame and display Main Menu
                // No - Do nothing. Continue with game

                if (!gameScreen.threadClass.gameOver() || (extendedModeOn && !gameScreen.threadClass2.gameOver())) {
//                if (!gameScreen.threadClass.gameOver() || (extendedModeOn )) {
                    int response = JOptionPane.showConfirmDialog(
                            gameScreen,
                            "Are you sure you want to stop the current game?",
                            "Quit Game",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (response == JOptionPane.YES_OPTION) {
                        gameScreen.threadClass.backButtonExitTriggered();
                        if (extendedModeOn) {
                            gameScreen.threadClass2.backButtonExitTriggered();
                        }
                        new MainMenuScreen().showMainScreen();
                        gameScreen.dispose();
                    } else {
                        // Resume the game if "No" is clicked
                        if (gameScreen.threadClass != null && gameScreen.threadClass.isGameRunning()) {
                            gameScreen.pauseLabel.setVisible(false);
                            gameScreen.threadClass.resumeGame();
                        }
                        if (extendedModeOn && (gameScreen.threadClass2 != null && gameScreen.threadClass2.isGameRunning())) {
                            gameScreen.pauseLabel2.setVisible(false);
                            gameScreen.threadClass2.resumeGame();
                        }
                    }
                } else {
                    new MainMenuScreen().showMainScreen();
                    gameScreen.dispose();
                }
            }
        });
    }
}