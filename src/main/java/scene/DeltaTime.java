package scene;

public class DeltaTime {
    private long start;

    public DeltaTime() {
        start();
    }

    public void start () {
        start = System.currentTimeMillis();
    }

    public double getDeltaTime () {
        long end = System.currentTimeMillis();
        double result = (end - start) / 1000.0;
        start = end;
        System.out.printf("FPS: %.2f\n", 1 / result);
        return result;
//        return 0.010;
    }
}
