package uk.co.bbc.iplayer.common.definition;

import java.io.Serializable;

public interface HasIdentity<T extends DataId> extends Serializable {
    T getId();
}
