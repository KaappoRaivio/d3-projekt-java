package sprite;

import misc.Pixel;
import misc.Vector2D;

public class Sprite {
    Pixel[][] pixelMatrix;
    private Vector2D dimensions;
    private Vector2D scale;

    public Sprite(Pixel[][] pixelMatrix) {
        this(pixelMatrix, Vector2D.of(1, 1));
    }

    public Sprite(Pixel[][] pixelMatrix, Vector2D scale) {
        this.pixelMatrix = pixelMatrix;
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
