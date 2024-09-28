package serverFiles;

import com.google.gson.Gson;
import settings.GameSettings;
import ui.GameBoard;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TetrisClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3000;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Gson gson;

    private GameBoard gameBoard;
    private PureGame pureGame;
    private GameSettings gameSettings = new GameSettings();

    public TetrisClient(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.pureGame = gameBoard.getPureGame();
        gameSettings = gameSettings.readSettingsFromJsonFile();
        this.gameBoard.setTetrisServerStatus(false);
        // Step 1: Establish a socket connection to the server
        try (
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {

            // Step 2: Convert PureGame object to JSON
            Gson gson = new Gson();
            String jsonGameState = gson.toJson(this.pureGame);

            // Step 3: Send the game state to the server
            out.println(jsonGameState);
//            System.out.println("Sent game state to server: " + jsonGameState);

            // Tells Game Board Server has connected.
            this.gameBoard.setTetrisServerStatus(true);

            // Step 4: Wait for the server's response (OpMove)
            String response = in.readLine();
//            System.out.println("Received response from server: " + response);

            // Step 5: Convert the JSON response to an OpMove object
            OpMove move = gson.fromJson(response, OpMove.class);
//            System.out.println("Optimal Move: X=" + move.opX() + ", Rotations=" + move.opRotate());

            // Step 6: Apply the move based on the opX and opRotate values
            this.gameBoard.serverMove(move.opX(), move.opRotate());

        } catch (IOException e) {
            // Display an error dialog with a message and an OK button
            JOptionPane.showMessageDialog(null,
                    "You need to start the TetrisServer to use external player mode",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}