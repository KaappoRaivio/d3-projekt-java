import entity.Entity;
import process.Process;

import java.util.List;

public class Scene {
    private List<Entity> entities;
    private List<Process> processes;

    public Scene(List<Entity> entities, List<Process> processes) {
        this.entities = entities;
        this.processes = processes;
    }

    public void play() {
        int frameCounter = 0;

        for (Process process : processes) {
            entities = process.update(entities, getDeltaTime(), frameCounter);
        }
//        new Scanner(System.in).nextLine();

        while (true) {
            for (Process process : processes) {
                long start = System.currentTimeMillis();
                entities = process.update(entities, getDeltaTime(), frameCounter);
                long end = System.currentTimeMillis();
                System.out.println("Process " + process.getClass() + " took " + (end - start) + " ms");
            }
//            try {
//                Thread.sleep(16);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            break;
            ++frameCounter;
        }
    }

    public double getDeltaTime () {
        return 0.01;
    }
}
