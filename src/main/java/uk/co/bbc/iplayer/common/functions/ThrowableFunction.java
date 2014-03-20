package uk.co.bbc.iplayer.common.functions;

/**
 * Created by spragn01 on 20/03/2014.
 */
public interface ThrowableFunction<T, S> {
    public S apply(T input) throws Exception;
}