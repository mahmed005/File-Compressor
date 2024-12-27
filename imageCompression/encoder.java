public class encoder {
    public String encodePixels(int[][] pixels , hashMap<Integer , String> encoding) {
        StringBuilder encodedData = new StringBuilder();
        for(int i=0;i<pixels.length;i++) {
            for(int j=0;j<pixels[0].length;j++){
                String encodedPixel = encoding.get(pixels[i][j]);
                encodedData.append(encodedPixel);
            }
        }
        return encodedData.toString();
    }
}
