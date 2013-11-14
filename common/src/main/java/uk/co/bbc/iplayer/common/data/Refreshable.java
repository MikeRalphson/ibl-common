package uk.co.bbc.iplayer.common.data;

public interface Refreshable {
    void refresh() throws RefreshFailedException;
}
