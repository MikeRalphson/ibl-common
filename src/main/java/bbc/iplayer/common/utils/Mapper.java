package bbc.iplayer.common.utils;

public interface Mapper<T, S> {
    S map(T input);

    Class<T> getSourceType();
}