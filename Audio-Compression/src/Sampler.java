import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.util.HashMap;

public class Sampler{
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        File file = new File("example.wav");

        AudioInputStream audio = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audio.getFormat();
        int sampleSize = format.getSampleSizeInBits() / 8;
        long totalFrames = audio.getFrameLength();

        byte[] audioBytes = new byte[(int) totalFrames * format.getFrameSize()];
        audio.read(audioBytes);

        int totalChannels = format.getChannels();
        int []samples = new int[(int) totalFrames * totalChannels];
        boolean isBigEndian = format.isBigEndian();

        int index = 0;
        for(int i = 0; i < audioBytes.length; i += sampleSize) {
            int sample = 0;

            if(sampleSize == 2) {
                if(isBigEndian)
                sample = (audioBytes[i] << 8) | (audioBytes[i+1] & 0xFF);
                else
                sample = (audioBytes[i+1] << 8) | (audioBytes[i] & 0xFF); 
            } else if(sampleSize == 1) {
                sample = audioBytes[i];
            }
            
            samples[index++] = sample;
        }

        HashMap<Integer, Integer> map = new HashMap<Integer , Integer>();

        for (int sample : samples) {
            map.put(sample, map.getOrDefault(sample, 1) + 1);
        }

        System.out.println("Sample frequencies:");
        for (int sample : map.keySet()) {
            System.out.println("Sample: " + sample + ", Count: " + map.get(sample));
        }
    }
}
