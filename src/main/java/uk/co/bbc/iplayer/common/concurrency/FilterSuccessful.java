package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

import java.util.List;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Iterables.filter;
import static uk.co.bbc.iplayer.common.concurrency.MoreFutures.await;

public class FilterSuccessful<T> implements ThrowableFunction<List<ListenableFuture<T>>, List<T>> {
    @Override
    public List<T> apply(List<ListenableFuture<T>> input) throws Exception {
        ListenableFuture<List<T>> pending = Futures.successfulAsList(input);
        // filter nulls and return list
        return Lists.newArrayList(filter(await(pending), notNull()));
    }
}
