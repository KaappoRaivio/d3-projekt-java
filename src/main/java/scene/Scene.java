package scene;

import entity.Entity;
import misc.Vector2D;
import process.Process;

import java.util.List;
import java.util.Scanner;

public class Scene {
    private List<Entity> entities;
    private List<Process> processes;
    private Vector2D maxSize;
    private DeltaTime timer;

    public Scene(List<Entity> entities, List<Process> processes, Vector2D maxSize) {
        this.entities = entities;
        this.processes = processes;
        this.maxSize = maxSize;
        timer = new DeltaTime();
    }

    public Vector2D getMaxSize() {
        return maxSize;
    }

    public void play() {
        play(-1);
    }

    public void play(int times) {

        timer.start();
        double deltaTime = timer.getDeltaTime();
        for (Process process : processes) {
            entities = process.update(this, entities, deltaTime, 0);
        }
//        new Scanner(System.in).nextLine();
//        timer.start();

        for (int frameCounter = 1; frameCounter != times; ++frameCounter) {
            deltaTime = timer.getDeltaTime();
            System.out.println("Current deltaTime: " + deltaTime);
            for (Process process : processes) {
                long start = System.currentTimeMillis();
                entities = process.update(this, entities, deltaTime, frameCounter);
                long end = System.currentTimeMillis();
                System.out.println("Process " + process.getClass() + " took " + (end - start) + " ms");
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
