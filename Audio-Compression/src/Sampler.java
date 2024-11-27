import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Sampler{
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        File file = new File("example.wav");

        AudioInputStream audio = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audio.getFormat();
        int sampleSize = format.getSampleSizeInBits();
        sampleSize /= 8;
        long totalFrames = audio.getFrameLength();

        byte[] audioBytes = new byte[(int) sampleSize * totalFrames * format.getFrameSize()]
    }
}
