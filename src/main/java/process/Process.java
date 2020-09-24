package process;

import entity.Entity;
import event.Event;
import scene.Scene;

import java.util.List;
import java.util.function.Function;

public interface Process {
    List<Entity> update(Scene scene, List<Entity> entities, double deltaTime, int frameCounter, Function<Event, Void> dispatchEvent);
    void onEvent(Event event);
}
