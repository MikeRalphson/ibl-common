package bbc.iplayer.common.utils;


public interface Converter<I, O> {
    public O convert(I core);
}
