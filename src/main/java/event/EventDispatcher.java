package event;

import java.util.List;
import process.Process;

public class EventDispatcher {
    private final List<Process> processes;

    public EventDispatcher (List<Process> processes) {
        this.processes = processes;
    }

    public void onEvent (Event event) {
        processes.forEach(process -> process.onEvent(event));
    }
}
