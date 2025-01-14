import ui.MainMenuScreen;
import ui.SplashScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen(1000);
        MainMenuScreen mainMenu = new MainMenuScreen();
        splash.showSplash();
        SwingUtilities.invokeLater(mainMenu::showMainScreen);
    }
}
