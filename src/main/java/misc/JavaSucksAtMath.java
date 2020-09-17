package misc;

import java.util.Arrays;

public class JavaSucksAtMath {
    public static int max (int... values) {
        return Arrays.stream(values)
                .reduce(Math::max)
                .orElseThrow();
    }

    public static double max (double... values) {
        return Arrays.stream(values)
                .reduce(Math::max)
                .orElseThrow();
    }

    public static int min(int... values) {
        return Arrays.stream(values)
                .reduce(Math::min)
                .orElseThrow();
    }

    public static double min(double... values) {
        return Arrays.stream(values)
                .reduce(Math::min)
                .orElseThrow();
    }
}
