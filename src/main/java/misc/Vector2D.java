package misc;

import java.util.Objects;

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

    public Vector2D divide (double other) {
        return multiply(1.0 / other);
    }

    public Vector2D multiply(double other) {
        return new Vector2D(i * other, j * other);
    }

    public Vector2D multiply(Vector2D other) {
        return new Vector2D(i * other.i, j * other.j);
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(i + other.i, j + other.j);
    }

    public Vector2D subtract (Vector2D other) {
        return add(other.multiply(-1));
    }

    public Vector2D abs () {
        return Vector2D.of(Math.abs(i), Math.abs(j));
    }

    public Vector2D coarsefy (int step) {
        return new Vector2D(Math.floor(i / step) * step, Math.floor(j / step) * step);
    }

    public double max () {
        return Math.max(i, j);
    }

    public double length () {
        return Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2));
    }

    public Vector2D normalize () {
        if (length() > 0) {
            return divide(length());
        } else {
            return Vector2D.of(0, 0);
        }
    }

    @Override
    public String toString() {
        return String.format("(%di + %dj)", Math.round(i), Math.round(j));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return Double.compare(vector2D.i, i) == 0 &&
                Double.compare(vector2D.j, j) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
