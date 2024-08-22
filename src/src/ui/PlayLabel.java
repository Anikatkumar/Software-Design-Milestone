package ui;

import javax.swing.*;
import java.awt.*;

public class PlayLabel extends JLabel {

    public PlayLabel(){
        this.setBounds(290, 4, 150, 150);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel jLabel = new JLabel("Play" , JLabel.CENTER);
        jLabel.setOpaque(true);
        jLabel.setForeground(Color.BLACK);
        jLabel.setFont(new Font("SansSerif", Font.BOLD, 30));

        add(jLabel);
        this.setVisible(true);
    }
}