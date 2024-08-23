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
        this.setBounds(300, 490, 70, 50);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Back");
        Dimension buttonSize = new Dimension(100, 50);
        backButton.setPreferredSize(buttonSize);
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.setForeground(Color.BLACK);
        this.add(backButton);
        this.setVisible(true);

//        backButton.addActionListener(new ActionListener() {
//                                         @Override
//                                         public void actionPerformed(ActionEvent e) {
//                                             System.out.println("back button clicked");
//                                             int response = JOptionPane.showConfirmDialog(null,
//                                                     "Do you want to Exit?", "Confirm Dialog", JOptionPane.YES_NO_OPTION,
//                                             JOptionPane.QUESTION_MESSAGE);
//                                             if (response == JOptionPane.YES_OPTION) {
//                                                 new MainMenuScreen().showMainScreen();
//
//                                             } else {
//                                             }
//                                         }
//                                     }
//        );
    }
}