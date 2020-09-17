package misc;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;

public class Pixel {
    private static final Map<Integer, TermColor> colorMap = Map.ofEntries(
            Map.entry(0, TermColor.ANSI_RED),
            Map.entry(1, TermColor.ANSI_YELLOW),
            Map.entry(2, TermColor.ANSI_GREEN),
            Map.entry(3, TermColor.ANSI_CYAN),
            Map.entry(4, TermColor.ANSI_BLUE),
            Map.entry(5, TermColor.ANSI_PURPLE)
    );
    private final int r;
    private final int g;
    private final int b;
    private final int a;

    public int getR () {
        return r;
    }

    public int getG () {
        return g;
    }

    public int getB () {
        return b;
    }

    public double getH () {
        var hsv = new float[3];
        Color.RGBtoHSB(r, g, b, hsv);
        return hsv[0];
    }

    public TermColor getColor () {
        long step = (Math.round(getH() * 6) % 6);


        return colorMap.get((int) step);
    }

    public double getS () {
        var hsv = new float[3];
        Color.RGBtoHSB(r, g, b, hsv);
        return hsv[1];
    }

    public double getV () {
        var hsv = new float[3];
        Color.RGBtoHSB(r, g, b, hsv);
        return hsv[2];
    }

    public String getAsText () {
        double v = Math.pow(getV(), 1);
        if (v < 0 || v > 1) {
            throw new RuntimeException("V should be 0 < v < 1, received " + v + "!");
        }

        double h = getH();
        double s = getS();
        String color;
        if (s > 0.5) {
            color = getColor().getEscape();
        } else {
            color = TermColor.ANSI_BLACK.getEscape();
        }
//        System.out.println(a);
        if (a == 0) {
            return TermColor.ANSI_WHITE.getEscape()+"▚";
        }

        return color + (v >= 0 && v < 0.25 ? "█"
                : v >= 0.25 && v < 0.5  ? "▓"
                : v >= 0.5  && v < 0.75 ? "▒"
                : v >= 0.75 && v <= 1.0  ? "░"
                : "F");

    }

    public Pixel(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Pixel(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Pixel blend (Pixel other) {
        double r2 = r / 256.0;
        double g2 = g / 256.0;
        double b2 = b / 256.0;
        double a2 = a / 256.0;

        double r1 = other.r / 256.0;
        double g1 = other.g / 256.0;
        double b1 = other.b / 256.0;
        double a1 = other.a / 256.0;

        var newA = a1 + a2 * (1 - a1);
        var newR = (r1 * a1 + r2 * a2 * (1 - a1)) / newA;
        var newG = (g1 * a1 + g2 * a2 * (1 - a1)) / newA;
        var newB = (b1 * a1 + b2 * a2 * (1 - a1)) / newA;
        return new Pixel((int) (newR * 256), (int) (newG * 256), (int) (newB * 256), (int) (newA * 256));
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %d, %d)", r, g, b, a);
    }

    public static void main(String[] args) {
        Pixel pixel1 = new Pixel(255, 0, 0, 255);
        Pixel pixel2 = new Pixel(0, 20, 0, 0);
        Pixel pixel3 = pixel1.blend(pixel2);
        System.out.println(pixel1 + "; " + pixel2 + "; " + pixel3);
        System.out.println(pixel1.getAsText() + "; " + pixel2.getAsText() + "; " + pixel3.getAsText());

    }

    public int getA() {
        return a;
    }
}
