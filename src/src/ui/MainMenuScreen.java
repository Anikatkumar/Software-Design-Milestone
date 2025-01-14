package ui;

import menuOptionButtons.ExitButton;
import settings.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainMenuScreen extends JFrame {

    // Setting Image Path Dynamically
    String basePath = new File("").getAbsolutePath();
    String pathToMenuIcon = basePath + "/src/src/resources/images/menuIcon.png";
    GameSettings gameSettings = new GameSettings();

    public void showMainScreen() {
        if (gameSettings.readSettingsFromJsonFile().isGameMusicOn()) {
            System.out.println("Music Main M Screen");
            GameBlock.playBackGroundMusic();
        }
        int width = 750;
        int height = 550;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

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

        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ceases to exist once showScreen() finishes execution.
                new GameScreen();
                dispose();
//                System.out.println("(MainMenuScreen) GameScreen disposed.");
            }
        });
        playButton.setBackground(Color.lightGray);
        playButton.setOpaque(true);

        JButton configButton = new JButton("Configurations");
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigurationScreen.getInstance().setVisible(true); // Get the Singleton instance

                //new ConfigurationScreen();
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

//        JButton exitButton = new JButton("Exit");
//        exitButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
//            }
//        });
        ExitButton exitButtonComponent = new ExitButton();
        JButton exitButton = exitButtonComponent.displayExitButton();

        exitButton.setBackground(Color.lightGray);
        exitButton.setOpaque(true);

        Dimension buttonSize = new Dimension(250, 50);
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