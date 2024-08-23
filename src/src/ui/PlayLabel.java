package ui;

import javax.swing.*;
import java.awt.*;

public class PlayLabel extends JLabel {

    public PlayLabel(String nameLabel, int x, int y){
        this.setBounds(x, y, 150, 150);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel jLabel = new JLabel(nameLabel , JLabel.CENTER);
        jLabel.setOpaque(true);
        jLabel.setForeground(Color.BLACK);
        jLabel.setFont(new Font("SansSerif", Font.BOLD, 30));

        add(jLabel);
        this.setVisible(true);
    }
}