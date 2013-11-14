package bbc.iplayer.ibl.common.utils;


public interface Converter<I, O> {
    public O convert(I core);
}
