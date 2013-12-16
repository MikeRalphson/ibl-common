package uk.co.bbc.iplayer.common.utils;

import java.util.List;

public interface Listenable {
    void registerListener(Listener listener);

    boolean removeListener(Listener listener);

    void notifyListeners(Object arg);

    List<Listener> getListeners();
}
