import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sampler{
    public int[] covertToSamples() throws UnsupportedAudioFileException, IOException {
        File file = new File("example2.wav");

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

        return samples;
    }

    public byte[] getHeader() {
        byte[] header = new byte[44];
        try {
            FileInputStream inputFile = new FileInputStream("example2.wav");
            inputFile.read(header);
        } catch(Exception e) {
            throw new Error("An exception occured");
        }
        return header; 
    }
}
