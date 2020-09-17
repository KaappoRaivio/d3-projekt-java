package process;

import entity.Entity;
import java.util.List;

public interface Process {
    List<Entity> update(List<Entity> entities, double deltaTime, int frameCounter);
}
