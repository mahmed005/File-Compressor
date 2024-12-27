import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class imageHandler {
    
    private BufferedImage image;
    private File input;
    
    public imageHandler(File file){
        try{
            image=ImageIO.read(file);
            input=file;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public int[][] imageToArray(){
            int width=image.getWidth();
            int height=image.getHeight();
            int [][] img=new int[width][height];

            for(int i=0;i<width;i++){
                for(int j=0;j<height;j++){
                    img[i][j]=image.getRGB(i, j);
                }
            }
            return img;
    }

    public int getHeight(){
        return image.getHeight();
    }

    public int getWidth(){
        return image.getWidth();
    }

    public byte[] getHeader() throws IOException {
        byte[] header=new byte[54];
        try (FileInputStream fis=new FileInputStream(input)){
            if (fis.read(header)!=54) {
                throw new IOException("Unable to read the header.");
            }
        }
        return header;
    }
}


 
