package process;

import entity.Entity;
import event.Event;
import event.EventType;
import event.KeyEvent;
import listener.KeyListener;
import misc.Vector2D;
import org.jnativehook.keyboard.NativeKeyEvent;
import scene.Scene;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Keyboard implements Process {
    private KeyListener listener;
    private volatile Set<Integer> keysDown = new HashSet<>();
    private Entity focusedEntity;

    public Keyboard (Entity focusedEntity) {
        this.focusedEntity = focusedEntity;
        listener = new KeyListener();
        listener.addKeyEventListener((e, keysDown) -> this.keysDown = keysDown);
        listener.run();
    }

    @Override
    public Set<Entity> update(Scene scene, Set<Entity> entities, double deltaTime, int frameCounter, Function<Event, Void> dispatchEvent) {
        keysDown.forEach(event -> dispatchEvent.apply(new KeyEvent(event)));
//        System.out.println(keysDown);
        keysDown.clear();

        return entities;
    }

    @Override
    public void onEvent(Event event, Function<Event, Void> dispatchEvent, Set<Entity> entities) {
        if (event.getEventType() == EventType.KEY) {
            KeyEvent keyEvent = (KeyEvent) event;
            System.out.println(event);
            if (keyEvent.getKeyCode() == NativeKeyEvent.VC_W) {
                focusedEntity.setVelocity(Vector2D.of(0, -100));
            } else if (keyEvent.getKeyCode() == NativeKeyEvent.VC_A) {
                focusedEntity.setVelocity(Vector2D.of(-100, 0));
            } else if (keyEvent.getKeyCode() == NativeKeyEvent.VC_D) {
                focusedEntity.setVelocity(Vector2D.of(100, 0));
            } else if (keyEvent.getKeyCode() == NativeKeyEvent.VC_S) {
                focusedEntity.setVelocity(Vector2D.of(0, 100));
            } else {
                focusedEntity.setVelocity(Vector2D.of(0, 0));
            }
        }
    }
}
