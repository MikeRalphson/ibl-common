package bbc.iplayer.common.utils;

import java.util.ArrayList;
import java.util.List;

public class ListenerStub implements Listener {

    private List<Object> myObjects = new ArrayList<Object>();

    @Override
    public void update(Listenable listenable, Object arg) {
        myObjects.add(arg);
    }

    public int getNotifyCount() {
        return myObjects.size();
    }
}
