package event;

import java.util.List;
import java.util.Set;

import entity.Entity;
import process.Process;

public class EventDispatcher {
    private final List<Process> processes;

    public EventDispatcher (List<Process> processes) {
        this.processes = processes;
    }

    public Void onEvent(Set<Entity> entities, Event event) {
        processes.forEach(process -> process.onEvent(event, event1 -> onEvent(entities, event1), entities));
        return null;
    }
}
