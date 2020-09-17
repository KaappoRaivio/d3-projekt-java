package misc;

public class Vector2D {
    private double i;
    private double j;
    public Vector2D(double i, double j) {
        super();

        this.i = i;
        this.j = j;
    }

    public static Vector2D of (double i, double j) {
        return new Vector2D(i, j);
    }

    public double getI() {
        return i;
    }

    public double getJ() {
        return j;
    }

    public int getIInt() {
        return (int) i;
    }

    public int getJInt() {
        return (int) j;
    }

    public Vector2D multiply(double other) {
        return new Vector2D(i * other, j * other);
    }

    public Vector2D multiply(Vector2D other) {
        return new Vector2D(i * other.getI(), j * other.getJ());
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(i + other.getI(), j + other.getJ());
    }

    @Override
    public String toString() {
        return String.format("(%di + %dj)", Math.round(i), Math.round(j));
    }
}
