package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scores.GameScores;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameScoresTests {
    private GameScores gameScores;

    @BeforeEach
    void setUp() {
        // Initialize gameScores and then set properties
        gameScores = new GameScores();
        gameScores.setPlayerName("Test Player");
        gameScores.setScore(100);
    }

    @Test
    void testScoreInitialization() {
        assertEquals("Test Player", gameScores.getPlayerName(), "Player name should be initialized correctly.");
        assertEquals(100, gameScores.getScore(), "Score should be initialized correctly.");
    }

    @Test
    void testScoreUpdate() {
        gameScores.setScore(150);
        assertEquals(150, gameScores.getScore(), "Score should be updated correctly.");
    }

    @Test
    void testPlayerNameUpdate() {
        gameScores.setPlayerName("New Player");
        assertEquals("New Player", gameScores.getPlayerName(), "Player name should be updated correctly.");
    }

    @Test
    void testReadScores() {
        // Assuming we are mocking the readScores method or it reads predefined values
        List<GameScores> scores = gameScores.readScores();
        assertNotNull(scores, "Scores list should not be null.");
    }

    @Test
    void testWriteAndClearScores() {
        List<GameScores> scoresList = new ArrayList<>();
        scoresList.add(new GameScores()); // Assuming default constructor usage
        scoresList.get(0).setPlayerName("Alice");
        scoresList.get(0).setScore(120);
        scoresList.add(new GameScores());
        scoresList.get(1).setPlayerName("Bob");
        scoresList.get(1).setScore(110);

        gameScores.writeListOfScoresInJSONFile(scoresList);
        assertNotNull(gameScores.readScores(), "Scores should not be null after writing.");

        gameScores.clearScores();
        assertTrue(gameScores.readScores().isEmpty(), "Scores should be empty after clearing.");
    }
}
