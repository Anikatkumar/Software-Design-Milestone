package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScreen extends JFrame{
    String pathToMenuIcon = "C:\\Users\\Sakshi\\Documents\\GitHub\\Software-Design-Milestone\\src\\src\\resources\\images\\menuIcon.png";
    public void showMainScreen(){

        int width = 750;
        int height = 550;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x,y,width,height);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("Tetris Game");
        ImageIcon menuIconImage = new ImageIcon(pathToMenuIcon);
        setIconImage(menuIconImage.getImage());

        JLabel titleLabel = new JLabel("Main Menu", JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 35));

        JLabel copyRightText = new JLabel("7805ICT, Assignment Group 14 - Copyright-2024 - Tetris", JLabel.CENTER);
        copyRightText.setOpaque(true);
        copyRightText.setForeground(Color.BLACK);
        copyRightText.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JPanel mainPanel = new JPanel(new GridLayout(6,1,10,10));
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameScreen().showScreen();
                dispose();

            }
        });
        playButton.setBackground(Color.lightGray);
        playButton.setOpaque(true);

        JButton configButton = new JButton("Configurations");
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConfigurationScreen();
                dispose();
            }
        });
        configButton.setBackground(Color.lightGray);
        configButton.setOpaque(true);

        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoresScreen();
                dispose();
            }
        });
        highScoresButton.setBackground(Color.lightGray);
        highScoresButton.setOpaque(true);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitButton.setBackground(Color.lightGray);
        exitButton.setOpaque(true);

        Dimension buttonSize = new Dimension(250,50);
        playButton.setPreferredSize(buttonSize);
        highScoresButton.setPreferredSize(buttonSize);
        configButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel6 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        panel1.add(titleLabel);
        panel2.add(playButton);
        panel3.add(configButton);
        panel4.add(highScoresButton);
        panel5.add(exitButton);
        panel6.add(copyRightText);


        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);
        mainPanel.add(panel4);
        mainPanel.add(panel5);
        mainPanel.add(panel6);

        add(mainPanel);

        /*add(playButton);
        add(configButton.displayConfigurationButton());
        add(highScoresButton);
        //add(highScoresButton.displayHighScoreButton());
        add(exitButton.displayExitButton());*/

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