import ui.SplashScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello world!");

        SplashScreen splash = new SplashScreen(5000);
        splash.showSplash();
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Main Application");
            mainFrame.setSize(800, 600);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
        });
    }
}