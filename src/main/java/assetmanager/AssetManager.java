package assetmanager;

import misc.Pixel;
import misc.TermColor;
import misc.Vector2D;
import sprite.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AssetManager {
    public static Pixel[][] getImage (String path) {
        File imageFile = new File(path);
        BufferedImage image;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        Pixel[][] finalImage = new Pixel[imageHeight][imageWidth];

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                Color color = new Color(image.getRGB(x, y), true);
                finalImage[y][x] = new Pixel(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            }
        }

        return finalImage;
    }

    public static void printImage (Pixel[][] image) {
        for (int y = 0; y < image.length; y++) {
            for (int x = 0; x < image[y].length; x++) {
                System.out.print(image[y][x].getAsText());
                System.out.print(image[y][x].getAsText());
                System.out.print(image[y][x].getAsText());
            }
            System.out.println();
        }
        System.out.print(TermColor.ANSI_RESET.getEscape());
    }
    public static void printImage (Sprite image) {
        for (int y = 0; y < image.getDimensions().getJ(); y++) {
            for (int x = 0; x < image.getDimensions().getI(); x++) {
                String asText = image.getPixelAt(Vector2D.of(x, y)).getAsText();
                System.out.print(asText);
                System.out.print(asText);
                System.out.print(asText);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
//        Pixel[][] image = AssetManager.getImage("/home/kaappo/git/d3/src/main/resources/graphics/testrainbow.png");
        Pixel[][] image = AssetManager.getImage("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png");
//        System.out.println(image[4][3]);
        AssetManager.printImage(image);
    }
}
