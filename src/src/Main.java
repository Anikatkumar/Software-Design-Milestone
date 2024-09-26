import AI.TetrisGameWithAI;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import settings.GameSettings;
import ui.GameBoard;
import ui.MainMenuScreen;
import ui.SplashScreen;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        SplashScreen splash = new SplashScreen(100);
        MainMenuScreen mainMenu = new MainMenuScreen();
//        splash.showSplash();
        SwingUtilities.invokeLater(mainMenu::showMainScreen);
        GameBoard gameBoard = new GameBoard(20);
        TetrisGameWithAI game = new TetrisGameWithAI(gameBoard);
        while (!game.isGameOver()) {
            game.render(); // Render the game
            game.dropPiece();
            }
    }
}
