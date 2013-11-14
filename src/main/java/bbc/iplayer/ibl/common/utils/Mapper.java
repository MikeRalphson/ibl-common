package bbc.iplayer.ibl.common.utils;

public interface Mapper<T, S> {
    S map(T input);

    Class<T> getSourceType();
}