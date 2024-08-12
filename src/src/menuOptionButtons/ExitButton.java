package menuOptionButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitButton extends JFrame {
    public Component displayExitButton(){
            JButton exitButton = new JButton("Exit Game");
            exitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            return exitButton;
    }
}
