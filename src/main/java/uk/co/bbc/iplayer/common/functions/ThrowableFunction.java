package uk.co.bbc.iplayer.common.functions;

public interface ThrowableFunction<T, S> {
    public S apply(T input) throws Exception;
}