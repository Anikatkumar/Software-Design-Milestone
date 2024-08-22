package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private static JPanel getButtonPanel(JFrame frame) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenuScreen().showMainScreen();
                frame.dispose();
            }
        });
        backButton.setBackground(Color.lightGray);
        backButton.setOpaque(true);
        Dimension buttonSize = new Dimension(150, 40);
        backButton.setPreferredSize(buttonSize);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        return buttonPanel;

    }
}

