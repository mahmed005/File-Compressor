public class Encoder {
    public String encodeSamples(int[] samples , HashMap<Integer , String> encoding) {
        String encodedData = "";
        for(int i = 0; i < samples.length; i++) {
            String encodedSample = encoding.get(samples[i]);
            encodedData += encodedSample;
        }
        return encodedData;
    }
}