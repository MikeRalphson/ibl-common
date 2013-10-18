package bbc.iplayer.ibl.common.utils;

import java.util.List;

public class ListenableStub implements Listenable {

    private DecoratedListener decoratedListener;

    public ListenableStub() {
        this.decoratedListener = new DecoratedListener(this);
    }

    @Override
    public void registerListener(Listener listener) {
        this.decoratedListener.registerListener(listener);
    }

    @Override
    public boolean removeListener(Listener listener) {
        return decoratedListener.removeListener(listener);
    }

    @Override
    public void notifyListeners(Object arg) {
        decoratedListener.notifyListeners(arg);
    }

    @Override
    public List<Listener> getListeners() {
        return decoratedListener.getListeners();
    }

}