package uk.co.bbc.iplayer.common.definition;

public interface HasIdentity<T extends DataId> {

    T getId();
}
