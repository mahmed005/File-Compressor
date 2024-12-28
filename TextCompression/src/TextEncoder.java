class TextEncoder {
    public String encodeText(String text, TextHashmap<Integer, String> encoding) {
        StringBuilder encodedData = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            int asciiValue = (int) text.charAt(i);
            String encodedAscii = encoding.get(asciiValue);
            if (encodedAscii != null) {
                encodedData.append(encodedAscii);
            } else {
                throw new IllegalArgumentException("ASCII value not found in encoding map: " + asciiValue);
            }
        }
        return encodedData.toString();
    }
}