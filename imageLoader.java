import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;

public class imageLoader {
    
    public imageLoader(){}

    public static int[][] imageToArray(File file){

        try{

            BufferedImage image=ImageIO.read(file);
            
            int width=image.getWidth();
            int height=image.getHeight();
            int [][] img=new int[width][height];

            for(int x=0;x<width;x++){
                for(int y=0;y<height;y++){
                    img[x][y]=image.getRGB(x, y);
                }
            }
            
            return img;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}


 
