package uk.co.bbc.iplayer.common.utils;

public interface RequestBuilder<T> extends Builder<T> {
    T from(T type);
}
