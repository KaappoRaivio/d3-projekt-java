package process;

import entity.Entity;
import scene.Scene;

import java.util.List;

public interface Process {
    List<Entity> update(Scene scene, List<Entity> entities, double deltaTime, int frameCounter);
}
