package menuOptionButtons;

import ui.MainMenuScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitButton {
    public JButton displayExitButton(){
        JButton exitButton = new JButton("Exit Game");
        exitButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to exit the game?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        return exitButton;
    }
}

