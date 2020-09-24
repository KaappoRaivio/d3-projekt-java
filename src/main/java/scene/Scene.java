package scene;

import entity.Entity;
import event.Event;
import event.EventDispatcher;
import misc.Vector2D;
import process.Collision;
import process.Process;

import java.util.List;
import java.util.function.Function;

public class Scene {
    private List<Entity> entities;
    private List<Process> processes;
    private Vector2D maxSize;
    private DeltaTime timer;
    private EventDispatcher dispatcher;

    public Scene(List<Entity> entities, List<Process> processes, Vector2D maxSize) {
        this.entities = entities;
        this.processes = processes;
        this.maxSize = maxSize;
        timer = new DeltaTime();
        dispatcher = new EventDispatcher(processes);
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

        Function<Event, Void> onEvent = event -> {
            dispatcher.onEvent(event);
//            System.out.println(event + ", " + event.getEventType());
            return null;
        };

        for (Process process : processes) {
            entities = process.update(this, entities, deltaTime, 0, onEvent);
        }
//        new Scanner(System.in).nextLine();
//        timer.start();
        for (int frameCounter = 1; frameCounter != times; ++frameCounter) {
            deltaTime = timer.getDeltaTime();
            System.out.println("Current deltaTime: " + deltaTime);
            for (Process process : processes) {
                long start = System.currentTimeMillis();
                entities = process.update(this, entities, deltaTime, frameCounter, onEvent);
                long end = System.currentTimeMillis();
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("COLLISIONS: " + ((Collision) processes.get(2)).getAmountOfCollisions());
        }
    }
}
