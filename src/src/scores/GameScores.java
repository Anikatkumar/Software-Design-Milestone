package scores;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import settings.GameSettings;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameScores {
    private String playerName;
    private int score;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "GameScores{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                '}';
    }


    public void writeScoresInJSONFile(GameScores gameScores) {
        /*
         * writing into json scores File
         * */
        // code to convert object to json
        // before writing score read existing and merge new one too
        List<GameScores> existingGameScores = readScores();
        existingGameScores.add(gameScores);

        Gson json = new Gson();
        try (FileWriter writer = new FileWriter("Scores.json")) {
            json.toJson(existingGameScores, writer);
            System.out.println("Score saved successfully!");
        } catch (IOException e) {
            System.out.println("Error Saving Score: " + e.getMessage());
        }
    }

    public GameScores readScoresFromJsonFile() {
        GameScores gameScores = null;
        try (FileReader reader = new FileReader("Scores.json")) {
            // Read the JSON file and map it to the Game Scores object
            Gson gson = new Gson();
            gameScores = gson.fromJson(reader, GameScores.class);
            // Output the Product object
        } catch (IOException | JsonIOException e) {
            System.out.println(e.getMessage());
        }
        return gameScores;
    }


    public List<GameScores> readScores() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("Scores.json")) {
            // Define the type for a List of Score objects
            Type scoreListType = new TypeToken<List<GameScores>>() {}.getType();
            // Read and return the list of scores
            return gson.fromJson(reader, scoreListType);
        } catch (IOException e) {
            // If the file doesn't exist or is empty, return empty list
            return new ArrayList<>();
        }
    }
}
