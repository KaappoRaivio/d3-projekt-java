package listener;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

class _KeyListener implements NativeKeyListener {
    private KeyListener wrapper;
    private boolean verbose;
    private List<KeyEventListener> keyEventListeners = new Vector<>();
    private Set<Integer> keysDown = new HashSet<>();

    public _KeyListener(KeyListener wrapper) {
        this(wrapper, true);
    }

    public void addKeyEventListener (KeyEventListener keyEventListener) {
        keyEventListeners.add(keyEventListener);
    }

    public void clearAllKeyListeners () {
        keyEventListeners = new Vector<>();
    }

    private _KeyListener (KeyListener wrapper, boolean verbose) {
        this.wrapper = wrapper;
        this.verbose = verbose;
    }

    private synchronized void broadcastKeyEvent (NativeKeyEvent e) {
        for (KeyEventListener keyEventListener : keyEventListeners) {
            keyEventListener.reportKey(e, keysDown);
        }
    }


    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (!keysDown.contains(e.getKeyCode())) {


            if (verbose) {
                System.out.println("Key pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()) + " " +  e.getKeyCode());
            }
            broadcastKeyEvent(e);
            keysDown.add(e.getKeyCode());
        }
	}

    @Override
	public void nativeKeyReleased(NativeKeyEvent e) {
        keysDown.remove(e.getKeyCode());
        broadcastKeyEvent(e);
        if (verbose) {
		    System.out.println("Key released: " + NativeKeyEvent.getKeyText(e.getKeyCode()) + " " +  e.getKeyCode());
        }
    }


    @Override
	public void nativeKeyTyped(NativeKeyEvent e) {
        if (verbose) {
		    System.out.println("Key typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()) + " " +  e.getKeyCode());
        }
    }
}
