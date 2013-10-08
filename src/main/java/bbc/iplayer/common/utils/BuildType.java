package bbc.iplayer.common.utils;

public interface BuildType<T, S extends Builder> {
    S using(T episode);
}