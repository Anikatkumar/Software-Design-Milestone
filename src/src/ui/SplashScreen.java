package ui;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    private final int duration;
    public SplashScreen(int duration) {
        this.duration = duration;
    }
    public void showSplash() {
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);
        int width = 350;
        int height = 550;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);
        JLabel label = new JLabel(new ImageIcon("E:\\Masters\\Sem3\\software design\\Project\\Software-Design-Milestone\\src\\src\\resources\\images\\SplashScreenRe1.png") , SwingConstants.CENTER);
        JLabel copyRightText = new JLabel("7805ICT, Assignment Group 14 - Copyright-2024 - Tetris", JLabel.CENTER);
        copyRightText.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(copyRightText, BorderLayout.SOUTH);
        Color oraRed = new Color(255,255,255);
        content.setBorder(BorderFactory.createLineBorder(oraRed, 10));
        setVisible(true);
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            System.out.println("Exception Occurred (SplashScreen): " + e.getMessage());
        }
        setVisible(false);
    }
    public void showSplashAndExit() {
        showSplash();
        System.exit(0);
    }
}
