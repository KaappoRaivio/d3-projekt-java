package event;

import org.jnativehook.keyboard.NativeKeyEvent;

public class KeyEvent extends Event {
    private int keyCode;

    public KeyEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public EventType getEventType() {
        return EventType.KEY;
    }

    public int getKeyCode () {
        return keyCode;
    }

    @Override
    public String toString() {
        return "KeyEvent{" +
                "keyCode=" + keyCode +
                '}';
    }
}
