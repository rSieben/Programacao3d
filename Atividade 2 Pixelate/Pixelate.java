import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pixelate {

    private static String Path = "D:\\Faculdade pucpr\\3o Periodo\\PROG 3D\\img\\cor";

    // PIXELATE


    private BufferedImage pixelate(BufferedImage img, int pixelSize){

        // get a BufferedImage object from file
        BufferedImage out = img;

        // loop through the image and produce squares pixelSize*pixelSize
        for(int w = 0 ; w < out.getWidth() ; w+=pixelSize)
        {
            for(int h = 0 ; h < out.getHeight() ; h+=pixelSize)
            {
                Color pixelColor = new Color(img.getRGB(w, h));

                Graphics graphics = out.getGraphics();
                graphics.setColor(pixelColor);
                graphics.fillRect(w, h, pixelSize, pixelSize);

            }
        }

        // output file
        return out;

    }

    private BufferedImage applyHSB(BufferedImage img){
        // 2.1- criar a imagem de retorno
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        // 2.2- transformar todos os pixeis
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                Color color = new Color(img.getRGB(x, y));

                float[] hsb = new float[3];
                Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);

                hsb[0] *= 1.0f; // Hue
                hsb[1] *= 1.0f; // Saturation
                hsb[2] *= 1.0f; // Brightness

                out.setRGB(x, y, Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            }
        }
        // 2.3- retornar a nova imagem
        return out;
    }

    private void run() throws IOException {
        //Carregar a imagem que vai ser processada
        BufferedImage img = ImageIO.read(new File(Path, "mario.jpg"));
        // aplicar as funções
        BufferedImage newImg = pixelate(img,10);
        //Salvar no disco a imagem resultante
        ImageIO.write(newImg, "png", new File(Path, "marioNew.png"));
    }

    public static void main(String[] args) throws IOException {
        new Pixelate().run();
    }

}