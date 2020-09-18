package scene;

import entity.Entity;
import process.Process;

import java.util.List;

public class Scene {
    private List<Entity> entities;
    private List<Process> processes;
    private DeltaTime timer;

    public Scene(List<Entity> entities, List<Process> processes) {
        this.entities = entities;
        this.processes = processes;
        timer = new DeltaTime();
    }

    public void play() {
        int frameCounter = 0;

        timer.start();
        double deltaTime = timer.getDeltaTime();
        for (Process process : processes) {
            entities = process.update(entities, deltaTime, frameCounter);
        }
//        new Scanner(System.in).nextLine();

        while (true) {
            deltaTime = timer.getDeltaTime();
            System.out.println("Current deltaTime: " + deltaTime);
            for (Process process : processes) {
                long start = System.currentTimeMillis();
                entities = process.update(entities, deltaTime, frameCounter);
                long end = System.currentTimeMillis();
                System.out.println("Process " + process.getClass() + " took " + (end - start) + " ms");
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            break;
            ++frameCounter;
        }
    }
}
