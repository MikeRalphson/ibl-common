package bbc.iplayer.common.utils;

import com.google.common.base.Preconditions;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ThreadSafe
public class DecoratedListener implements Listenable {

    private Listenable listenable;
    private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();

    public DecoratedListener(Listenable listenable) {
        this.listenable = listenable;
    }

    public void registerListener(Listener listener) {
        Preconditions.checkNotNull(listener);
        listeners.add(listener);
    }

    public boolean removeListener(Listener listener) {
        Preconditions.checkNotNull(listener);
        return listeners.remove(listener);
    }

    public void notifyListeners(Object arg) {
        for (Listener listener : listeners) {
            listener.update(listenable, arg);
        }
    }

    @Override
    public List<Listener> getListeners() {
        return Collections.unmodifiableList(listeners);
    }
}