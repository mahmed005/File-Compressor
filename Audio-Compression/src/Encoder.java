public class Encoder {
    public String encodeSamples(int[] samples , HashMap<Integer , String> encoding) {
        StringBuilder encodedData = new StringBuilder();
        for(int i = 0; i < samples.length; i++) {
            String encodedSample = encoding.get(samples[i]);
            encodedData.append(encodedSample);
        }
        return encodedData.toString();
    }
}