package infra;

import java.util.List;
import java.util.function.Consumer;

public class UnaryPubSub<T> {
    private final List<Consumer<T>> listeners;

    public UnaryPubSub(List<Consumer<T>> listeners) {
        this.listeners = listeners;
    }

    public void update(T update) {
        listeners.forEach(l -> l.accept(update));
    }
}
