package ui;

import menuOptionButtons.ConfigButton;
import menuOptionButtons.ExitButton;
import menuOptionButtons.HighScoresButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScreen extends JFrame{
    String pathToMenuIcon = "E:\\Masters\\Sem3\\software design\\Project\\Software-Design-Milestone\\src\\src\\resources\\images\\menuIcon.png";
    public void showMainScreen(){
        ExitButton exitButton = new ExitButton();
        HighScoresButton highScoresButton = new HighScoresButton();
        ConfigButton  configButton = new ConfigButton();

        int width = 750;
        int height = 550;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;

        setLayout(new GridLayout(7,2,20,50));
        setBounds(x,y,width,height);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("Tetris Game");
        ImageIcon menuIconImage = new ImageIcon(pathToMenuIcon);
        setIconImage(menuIconImage.getImage());

        JLabel menuTopLabel = new JLabel("Main Menu");
        menuTopLabel.setOpaque(true);
        menuTopLabel.setForeground(Color.BLACK);
        menuTopLabel.setFont(new Font("SansSerif", Font.BOLD, 40));


        add(menuTopLabel);
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameScreen().showScreen();
                dispose();

            }
        });
        add(playButton);
        add(configButton.displayConfigurationButton());
        add(highScoresButton.displayHighScoreButton());
        add(exitButton.displayExitButton());








    }
}

//int width = 750;
//int height = 550;
//Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//int x = (screen.width - width) / 2;
//int y = (screen.height - height) / 2;
//JLabel menuScreenLabel = new JLabel("Menu Screen" , JLabel.CENTER);
//        menuScreenLabel.setFont(new Font("Sans-Serif", Font.BOLD, 12));
//JFrame jf = new JFrame();
//        jf.add(menuScreenLabel, BorderLayout.SOUTH);
//        jf.setBounds(x, y, width, height);
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jf.setVisible(true);


/*
package ui;

import menuOptionButtons.ConfigButton;
import menuOptionButtons.ExitButton;
import menuOptionButtons.HighScoresButton;
import menuOptionButtons.PlayButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainMenuScreen extends JFrame{
    String pathToMenuIcon = "E:\\Masters\\Sem3\\software design\\Project\\Software-Design-Milestone\\src\\src\\resources\\images\\menuIcon.png";
    public void showMainScreen(){
        ExitButton exitButton = new ExitButton();
        HighScoresButton highScoresButton = new HighScoresButton();
        ConfigButton  configButton = new ConfigButton();
        PlayButton playButton = new PlayButton();
        JFrame screenFrame = new JFrame();

        screenFrame.setTitle("Tetris Game");
        ImageIcon menuIconImage = new ImageIcon(pathToMenuIcon);
        screenFrame.setIconImage(menuIconImage.getImage());

        int width = 750;
        int height = 550;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;

        JLabel menuTopLabel = new JLabel("Main Menu");
        menuTopLabel.setOpaque(true);
        menuTopLabel.setForeground(Color.BLACK);
        menuTopLabel.setFont(new Font("SansSerif", Font.BOLD, 20));


        screenFrame.add(menuTopLabel);
        screenFrame.add(playButton.displayPlayButton());
        screenFrame.add(configButton.displayConfigurationButton());
        screenFrame.add(highScoresButton.displayHighScoreButton());
        screenFrame.add(exitButton.displayExitButton());





        screenFrame.setLayout(new FlowLayout());
        screenFrame.setBounds(x,y,width,height);
        screenFrame.setVisible(true);
        screenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

//int width = 750;
//int height = 550;
//Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//int x = (screen.width - width) / 2;
//int y = (screen.height - height) / 2;
//JLabel menuScreenLabel = new JLabel("Menu Screen" , JLabel.CENTER);
//        menuScreenLabel.setFont(new Font("Sans-Serif", Font.BOLD, 12));
//JFrame jf = new JFrame();
//        jf.add(menuScreenLabel, BorderLayout.SOUTH);
//        jf.setBounds(x, y, width, height);
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jf.setVisible(true);

* **/