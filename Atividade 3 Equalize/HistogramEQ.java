import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HistogramEQ {

    public static final String PATH = "D:\\Faculdade pucpr\\3o Periodo\\PROG 3D\\img\\gray";

    int[] calcula_Histograma(BufferedImage img){
        int[] histograma = new int[256];
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color color = new Color(img.getRGB(x, y));
                int red = color.getRed();
                histograma[red] += 1;
            }
        }
        return histograma;
    }


    public int[] calculaHistogramaAcumulado(int[] histograma) {
        int[] acumulado = new int[256];
        acumulado[0] = histograma[0];
        for(int i=1;i < histograma.length;i++) {

            acumulado[i] = histograma[i] + acumulado[i-1];
        }
        return acumulado;
    }

    private int menorValor(int[] histograma) {
        for(int i=0; i <histograma.length; i++) {
            if(histograma[i] != 0){
                return i;
            }
        }
        return 0;
    }

    private int[] calculaMapadeCores(int[] histograma, int pixels) {
        int[] mapaDeCores = new int[256];
        int[] acumulado = calculaHistogramaAcumulado(histograma);
        float menor = menorValor(histograma);
        for(int i=0; i < histograma.length; i++) {
            mapaDeCores[i] = Math.round(((acumulado[i] - menor) / (pixels - menor)) * 255);
        }
        return mapaDeCores;
    }

    public BufferedImage equalizacao(BufferedImage img) {
        //2.1- calcular histograma
        int[] histograma = calcula_Histograma(img);
        //2.2- calcular novo mapa de cores
        int[] mapaDeCores = calculaMapadeCores(histograma, img.getWidth() * img.getHeight());
        //2.3- atualizar palheta de cores da imagem
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color color = new Color(img.getRGB(x, y));
                int tom = color.getRed();
                int newTom = mapaDeCores[tom];
                Color newColor = new Color(newTom, newTom, newTom);
                out.setRGB(x, y, newColor.getRGB());
            }
        }
        //2.4- retornar imagem processada
        return out;
    }

    void run() throws IOException {
        //1-carregar a imagem
        BufferedImage img = ImageIO.read(new File(PATH, "lizard.png"));
        //2-equalizar
        BufferedImage newImg = equalizacao(img);
        //3-salvar imagem resultante
        ImageIO.write(newImg, "png", new File(PATH, "ImagemProcessada.png"));
    }

    public static void main(String[] args) throws IOException {
        new HistogramEQ().run();
    }

}