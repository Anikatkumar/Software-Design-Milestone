package ui;

import javax.swing.*;
import java.awt.*;

public class GameDisplayFacade {
    public void showScreen(GameScreen gameScreen, int width, int height) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;

        gameScreen.setBounds(x, y, width, height);
        gameScreen.setVisible(true);
        gameScreen.setTitle("Play");
        gameScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
