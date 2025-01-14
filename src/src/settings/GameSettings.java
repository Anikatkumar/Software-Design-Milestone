package settings;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameSettings {
    private boolean aiModeOn;
    private int fieldWidth;
    private int fieldHeight;
    private int initialGameLevel;
    private boolean gameMusicOn;
    private boolean gameSoundsOn;
    private boolean extendModeOn;
    private String playerOneType;
    private String playerTwoType;

    // Getters and setters
    public boolean getAiModeOn() {
        return aiModeOn;
    }

    public void setAiModeOn(boolean aiModeOn) {
        this.aiModeOn = aiModeOn;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public int getGameLevel() {
        return initialGameLevel;
    }

    public void setGameLevel(int gameLevel) {
        this.initialGameLevel = gameLevel;
    }

    public boolean isGameMusicOn() {
        return gameMusicOn;
    }

    public void setGameMusicOn(boolean gameMusicOn) {
        this.gameMusicOn = gameMusicOn;
    }

    public boolean isGameSoundsOn() {
        return gameSoundsOn;
    }

    public void setGameSoundsOn(boolean gameSoundsOn) {
        this.gameSoundsOn = gameSoundsOn;
    }

    public boolean isExtendModeOn() {
        return extendModeOn;
    }

    public void setExtendModeOn(boolean extendModeOn) {
        this.extendModeOn = extendModeOn;
    }

    public String getPlayerOneType() {
        return playerOneType;
    }
    
    public void setPlayerOneType(String playerOneType) {
        this.playerOneType = playerOneType;
    }

    public String getPlayerTwoType() {
        return playerTwoType;
    }

    public void setPlayerTwoType(String playerTwoType) {
        this.playerTwoType = playerTwoType;
    }

    // Save settings to JSON file
    public void writeSettingsIntoJsonFile(GameSettings settings) {
        Gson json = new Gson();
        try (FileWriter writer = new FileWriter("Configurations.json")) {
            json.toJson(settings, writer);
            System.out.println("Settings saved successfully!");
        } catch (IOException e) {
            System.out.println("Error Saving Settings: " + e.getMessage());
        }
        System.out.println("New Settings: " + settings.toString());
    }

    // Read settings from JSON file
    public GameSettings readSettingsFromJsonFile() {
        GameSettings gameSettings = null;
        try (FileReader reader = new FileReader("Configurations.json")) {
            Gson gson = new Gson();
            gameSettings = gson.fromJson(reader, GameSettings.class);
        } catch (IOException | JsonIOException e) {
            System.out.println(e.getMessage());
        }
        return gameSettings;
    }

    @Override
    public String toString() {
        return "GameSettings{" +
                "aiModeOn=" + aiModeOn +
                ", fieldWidth=" + fieldWidth +
                ", fieldHeight=" + fieldHeight +
                ", initialGameLevel=" + initialGameLevel +
                ", gameMusicOn=" + gameMusicOn +
                ", gameSoundsOn=" + gameSoundsOn +
                ", extendModeOn=" + extendModeOn +
                ", playerOneType='" + playerOneType + '\'' +
                ", playerTwoType='" + playerTwoType + '\'' +
                '}';
    }
}
