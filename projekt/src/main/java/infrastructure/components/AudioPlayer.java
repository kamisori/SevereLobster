package infrastructure.components;

import infrastructure.constants.GlobaleKonstanten;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author fwenisch
 */
public abstract class AudioPlayer {
    /**
     * Spielt die Datei <strName> im SoundVerzeichnis ab
     *
     * @param strName
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public static void playSound(String strName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(GlobaleKonstanten.DEFAULT_SOUND_DIR, strName));
        BufferedInputStream bufferedInputStream = new BufferedInputStream(audioInputStream);
        AudioFormat af = audioInputStream.getFormat();
        int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
        byte[] audio = new byte[size];
        DataLine.Info info = new DataLine.Info(Clip.class, af, size);
        bufferedInputStream.read(audio, 0, size);
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(af, audio, 0, size);
        clip.start();
    }

    /**
     * Spielt den Siegessound ab
     *
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public static void playWinSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(GlobaleKonstanten.DEFAULT_SOUND_DIR, "win.wav"));
        BufferedInputStream bufferedInputStream = new BufferedInputStream(audioInputStream);
        AudioFormat af = audioInputStream.getFormat();
        int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
        byte[] audio = new byte[size];
        DataLine.Info info = new DataLine.Info(Clip.class, af, size);
        bufferedInputStream.read(audio, 0, size);
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(af, audio, 0, size);
        clip.start();
    }

}
