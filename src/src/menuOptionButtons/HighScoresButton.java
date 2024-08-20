package menuOptionButtons;

import ui.HighScoresScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighScoresButton extends JFrame {
    public Component displayHighScoreButton(){
        JButton highScoreButton = new JButton("High Scores");
        highScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HighScoresScreen();
                dispose();
            }
        });
        return highScoreButton;
    }
}
