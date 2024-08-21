package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButton extends JButton {
    private JButton backButton = new JButton("Back");

    public BackButton() {
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
                                             System.out.println("back button clicked");


                                         }
                                     }
        );
    }
}