package uk.co.bbc.iplayer.common.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class DecoratedListenerTest {

    private ListenableStub listenable;

    @Before
    public void setup() {
        listenable = new ListenableStub();
    }

    @Test
    public void addListeners() {
        // verify no listeners
        assertThat(listenable.getListeners().size(), is(0));

        int expectedNumberOfListeners = 10;
        addNListeners(expectedNumberOfListeners);

        assertThat(listenable.getListeners().size(), is(expectedNumberOfListeners));
    }

    @Test
    public void removeListeners() {
        int total = 10;
        addNListeners(total);

        List<Listener> listeners = listenable.getListeners();
        Listener myListener = listeners.get(0);

        // initial count check...
        assertThat(listenable.getListeners().size(), is(total));

        // remove a listener
        listenable.removeListener(myListener);
        assertThat(listenable.getListeners().size(), is(total - 1));

        // ensure the right listener has been removed
        for (Listener listener : listenable.getListeners()) {
            assertThat(listener, not(sameInstance(myListener)));
        }
    }

    @Test
    public void notifyListeners() {
        addNListeners(10);

        // verify no notifications have been made
        verifyListenerNotificationCount(0);

        listenable.notifyListeners(new Object());

        // verify each listener has been notified once
        verifyListenerNotificationCount(1);

        // guess what?
        listenable.notifyListeners(new Object());
        verifyListenerNotificationCount(2);
    }

    private void verifyListenerNotificationCount(int i) {
        for (Listener listener : listenable.getListeners()) {
            ListenerStub myListener = (ListenerStub) listener;
            assertThat(myListener.getNotifyCount(), is(i));
        }
    }

    private void addNListeners(int n) {
        for (int i = 0; i < n; i++) {
            listenable.registerListener(new ListenerStub());
        }
    }

}
