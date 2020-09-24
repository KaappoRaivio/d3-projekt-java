package process;

import entity.Entity;
import event.Event;
import scene.Scene;

import java.util.Set;
import java.util.function.Function;

public interface Process {
    Set<Entity> update(Scene scene, Set<Entity> entities, double deltaTime, int frameCounter, Function<Event, Void> dispatchEvent);
    void onEvent(Event event, Function<Event, Void> dispatchEvent);
}
