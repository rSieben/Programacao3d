import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Rodrigo Sieben on 17/03/2017.
 */
public class DistanciaCores {

    static String Path = "D:/workspace/Kernel/img/cor/";

    static String listaEAG[] = {
            "#000000",
            "#0000AA",
            "#00AA00",
            "#00AAAA",
            "#AA0000",
            "#AA00AA",
            "#AAAA00",
            "#AAAAAA",
            "#000055",
            "#0000FF",
            "#00AA55",
            "#00AAFF",
            "#AA0055",
            "#AA00FF",
            "#AAAA55",
            "#AAAAFF",
            "#005500",
            "#0055AA",
            "#00FF00",
            "#00FFAA",
            "#AA5500",
            "#AA55AA",
            "#AAFF00",
            "#AAFFAA",
            "#005555",
            "#0055FF",
            "#00FF55",
            "#00FFFF",
            "#AA5555",
            "#AA55FF",
            "#AAFF55",
            "#AAFFFF",
            "#550000",
            "#5500AA",
            "#55AAAA",
            "#FF0000",
            "#FF00AA",
            "#FFAA00",
            "#FFAAAA",
            "#550055",
            "#5500FF",
            "#55AA55",
            "#55AAFF",
            "#FF0055",
            "#FF00FF",
            "#FFAA55",
            "#FFAAFF",
            "#555500",
            "#5555AA",
            "#55FF00",
            "#55FFAA",
            "#FF5500",
            "#FF55AA",
            "#FFFF00",
            "#FFFFAA",
            "#555555",
            "#5555FF",
            "#55FF55",
            "#FF5555",
            "#FF55FF",
            "#FFFF55",
            "#FFFFFF"
    };

    private double calcularDistanciaDeCores(Color cor1, Color cor2){

        long mediaR = (cor1.getRed() + cor2.getRed())/2;

        long r = cor1.getRed() - cor2.getRed();
        long g = cor1.getGreen() - cor2.getGreen();
        long b = cor1.getBlue() - cor2.getBlue();

        return Math.sqrt((2+mediaR/256)*Math.pow(r, 2)+4*Math.pow(g, 2)
                +(2+(255-mediaR)/256)*Math.pow(b, 2));
    }

    public Color getColor(String hex){
        int  r =  Integer.valueOf( hex.substring( 1, 3 ), 16 );
        int  g =  Integer.valueOf( hex.substring( 3, 5 ), 16 );
        int  b =  Integer.valueOf( hex.substring( 5, 7 ), 16 );
        return new Color(r,g,b);
    };

    public Color procuraCorParecida(Color cor){
        double lastColorDistance = 0;
        int corMaisParecida = 0;

        for (int i = 0; i < listaEAG.length; i++){

            double colorDistance = calcularDistanciaDeCores(cor,getColor(listaEAG[i]));

            if( colorDistance < lastColorDistance || i == 0){
                lastColorDistance = colorDistance;
                corMaisParecida = i;
            }

        }

        return getColor(listaEAG[corMaisParecida]);

    }

    BufferedImage convertToEgaPallet(BufferedImage img){

        BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color corParecida = procuraCorParecida(new Color(img.getRGB(x,y)));
                out.setRGB(x,y,corParecida.getRGB());
            }
        }

        return out;
    }

    public void Run() throws IOException {
        BufferedImage img = ImageIO.read(new File(Path, "turtle.jpg"));
        ImageIO.write(convertToEgaPallet(img), "png", new File(Path,"egaColoredTurtle.png"));
    }

    public static void main(String args[]) throws IOException {
        new DistanciaCores().Run();
    }

}
