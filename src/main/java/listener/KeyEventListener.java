package listener;

import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.Set;

public interface KeyEventListener {
    void reportKey(NativeKeyEvent event, Set<Integer> keysDown);
}
