package event;

import java.util.List;
import java.util.function.Function;

import process.Process;

public class EventDispatcher {
    private final List<Process> processes;

    public EventDispatcher (List<Process> processes) {
        this.processes = processes;
    }

    public Void onEvent(Event event) {
        processes.forEach(process -> process.onEvent(event, this::onEvent));
        return null;
    }
}
