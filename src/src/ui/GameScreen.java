package ui;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JFrame {
    private  JPanel GameBoard;
    private GameBoard gameBoard;
    public GameScreen(){
        gameBoard = new GameBoard(10);
        this.add(gameBoard);
        new DelayClass(gameBoard).start();
    }

    public void showScreen(){
        GameScreen gameScreen = new GameScreen();
        int width = 750;
        int height = 600;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        gameScreen.setLayout(new BorderLayout());
        gameScreen.setBounds(x,y,width,height);
        gameScreen.setVisible(true);
        gameScreen.setTitle("Play");
        gameScreen.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
