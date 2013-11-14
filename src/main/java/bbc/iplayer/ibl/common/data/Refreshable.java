package bbc.iplayer.ibl.common.data;

public interface Refreshable {
    void refresh() throws RefreshFailedException;
}
