package uk.co.bbc.iplayer.common.utils;

// Listener to remove itself from the Listenable's notification list
public class SelfRemovingListener extends ListenerStub {
    @Override
    public void update(Listenable listenable, Object arg) {
        super.update(listenable, arg);
        listenable.removeListener(this);
    }
}