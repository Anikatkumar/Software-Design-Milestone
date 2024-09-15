package ui;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoundPlayer {

    String musicFolderPath = new File("").getAbsolutePath();
    String backgroundMusicPath = musicFolderPath + "/src/src/audioFiles/background.wav";
    String gameFinishSoundPath = musicFolderPath + "/src/src/audioFiles/game-finish.wav";
    String moveTurnSoundPath = musicFolderPath + "/src/src/audioFiles/move-turn.wav";
    String eraseLineSoundPath = musicFolderPath + "/src/src/audioFiles/erase-line.wav";
    String levelUpSoundPath = musicFolderPath + "/src/src/audioFiles/level-up.wav";

    private Clip backgroundMusic, moveTurnMusic, eraseLineMusic, levelUpMusic, gameFinishMusic;

    public SoundPlayer() {
        try {
            backgroundMusic = AudioSystem.getClip();
            moveTurnMusic = AudioSystem.getClip();
            eraseLineMusic = AudioSystem.getClip();
            levelUpMusic = AudioSystem.getClip();
            gameFinishMusic = AudioSystem.getClip();

//            backgroundMusic.open(AudioSystem.getAudioInputStream(new File(backgroundMusicPath).getAbsoluteFile()));
            backgroundMusic.open(AudioSystem.getAudioInputStream(new File(backgroundMusicPath)));
            moveTurnMusic.open(AudioSystem.getAudioInputStream(new File(moveTurnSoundPath).getAbsoluteFile()));
            eraseLineMusic.open(AudioSystem.getAudioInputStream(new File(eraseLineSoundPath).getAbsoluteFile()));
            levelUpMusic.open(AudioSystem.getAudioInputStream(new File(levelUpSoundPath).getAbsoluteFile()));
            gameFinishMusic.open(AudioSystem.getAudioInputStream(new File(gameFinishSoundPath).getAbsoluteFile()));
        } catch (LineUnavailableException lue) {
            System.out.println("(SoundPlayer) Error: " + lue.getMessage());
        } catch (UnsupportedAudioFileException | IOException ue) {
            Logger.getLogger(SoundPlayer.class.getName()).log(Level.SEVERE, null, ue);
        }

    }

    public void playBackgroundMusic() {
        System.out.println("Background Music");
        backgroundMusic.setFramePosition(0);
        backgroundMusic.start();
    }

    public void playMoveTurnMusic() {
        moveTurnMusic.setFramePosition(0);
        moveTurnMusic.start();
    }

    public void playEraseLineMusic() {
        System.out.println("EraseLine Music");
        eraseLineMusic.setFramePosition(0);
        eraseLineMusic.start();
    }

    public void playLevelUpMusic() {
        levelUpMusic.setFramePosition(0);
        levelUpMusic.start();
    }

    public void playGameFinishMusic() {
        gameFinishMusic.setFramePosition(0);
        gameFinishMusic.start();
    }
}
