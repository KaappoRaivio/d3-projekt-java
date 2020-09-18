package sprite;

import misc.Pixel;
import misc.Vector2D;

import java.awt.image.BufferedImage;

public class Sprite {
    Pixel[][] pixelMatrix;
    private Vector2D dimensions;
    private Vector2D scale;
    private BufferedImage originalImage;

    public Sprite(String assetPath) {
        this(assetPath, Vector2D.of(1, 1));
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public Sprite(String assetPath, Vector2D scale) {
        this.originalImage = AssetManager.getImage(assetPath);
        this.pixelMatrix = AssetManager.getPixels(originalImage);
        this.dimensions = Vector2D.of(pixelMatrix[0].length, pixelMatrix.length);
        this.scale = scale;
    }

    public Pixel getPixelAt(Vector2D position) {
        return this.pixelMatrix[position.getJInt() / scale.getJInt()][position.getIInt() / scale.getIInt()];
    }

    public Vector2D getDimensions() {
        return dimensions.multiply(scale);
    }
}
