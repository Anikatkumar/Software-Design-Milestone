
package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import settings.GameSettings;

class GameSettingsTests {

    @Test
    void testDefaultSettings() {
        GameSettings settings = new GameSettings();
        assertFalse(settings.isGameMusicOn(), "Default game music should be off");
        assertFalse(settings.isGameSoundsOn(), "Default game sounds should be off");
        assertFalse(settings.getAiModeOn(), "Default AI mode should be off");
        assertEquals(0, settings.getFieldWidth(), "Default field width should be 10");
        assertEquals(0, settings.getFieldHeight(), "Default field height should be 20");
    }

    @Test
    void testSetAndGetGameMusicOn() {
        GameSettings settings = new GameSettings();
        settings.setGameMusicOn(true);
        assertTrue(settings.isGameMusicOn(), "Game music should be on when set to true");
    }

    @Test
    void testSaveAndLoadSettings() {
        GameSettings settings = new GameSettings();
        settings.setGameMusicOn(true);
        settings.setFieldWidth(12);
        settings.writeSettingsIntoJsonFile(settings);

        GameSettings loadedSettings = settings.readSettingsFromJsonFile();
        assertTrue(loadedSettings.isGameMusicOn(), "Loaded game music setting should match saved setting");
        assertEquals(12, loadedSettings.getFieldWidth(), "Loaded field width should match saved setting");
    }
}

