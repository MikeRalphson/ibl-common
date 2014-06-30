package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static uk.co.bbc.iplayer.common.concurrency.Duration.inMilliSeconds;

/**
 * Patch for 'aggregated futures' (called within Guava's successfulAsList) that never return by enforcing timeout.
 * If timeout or interrupt exceptions are thrown an attempt will be made to cancel any futures
 * that are still running.
 */
public class MoreFutures {

    private final static boolean INTERRUPT_TASK = true;
    private static final Logger LOG = LoggerFactory.getLogger(MoreFutures.class);

    private MoreFutures() {
        throw new AssertionError();
    }

    public static <T> T await(ListenableFuture<? extends T> future, Duration duration) throws MoreFuturesException {

        try {
            return future.get(duration.getLength(), duration.getTimeUnit());

        } catch (InterruptedException e) {
            log("await", e);
            throw new MoreFuturesException("Future interrupted", e);

        } catch (ExecutionException e) {
            log("await", e);
            throw new MoreFuturesException("Future Execution Exception", e);
        } catch (TimeoutException e) {
            log("await", e);
            if (future instanceof IdentifyingFuture) {
                throw new MoreFuturesException("Timed out: " + ((IdentifyingFuture) future).getDescriptor(), e);
            }

            throw new MoreFuturesException("Timed out" + future.toString(), e);
        } finally {
            if (!future.isDone()) {
                future.cancel(INTERRUPT_TASK);
            }
        }
    }

    public static <T> List<T> aggregate(Duration timeout, ListenableFuture<? extends T>... futures) throws MoreFuturesException {
        Iterable<? extends ListenableFuture<? extends T>> futuresList = Lists.newArrayList(futures);
        return aggregate(futuresList, timeout);
    }


    public static <T> List<T> aggregate(Iterable<? extends ListenableFuture<? extends T>> futures, Duration timeout) throws MoreFuturesException {
        List<T> results = Collections.EMPTY_LIST;
        try {
            results = Futures.successfulAsList(futures).get(timeout.getLength(), timeout.getTimeUnit());
        } catch (InterruptedException e) {
            log("aggregate InterruptedException", e);
            throw new MoreFuturesException("Future interrupted", e);
        } catch (ExecutionException e) {
            log("aggregate ExecutionException", e);
            throw new MoreFuturesException("Execution exception", e);
        } catch (TimeoutException e) {
            log("aggregate TimeoutException", e);
            // Extract the successful futures and cancel futures that are stilling running
            return filterCompleteTasks(futures);
        } finally {
            cancelActiveFutures(futures);
            results = newArrayList(filter(results, notNull()));
        }

        return results;
    }

    private static void log(String method, Exception e) {
        if (LOG.isWarnEnabled()) {
            LOG.warn(method + "," + ExceptionUtils.getFullStackTrace(e));
        }
    }

    private static <T> void cancelActiveFutures(final Iterable<? extends ListenableFuture<? extends T>> futures) {

        synchronized (futures) {
            for (ListenableFuture future : futures) {
                if (!future.isDone()) {
                    future.cancel(INTERRUPT_TASK);
                }
            }
        }
    }

    private static <T> List<T> filterCompleteTasks(Iterable<? extends ListenableFuture<? extends T>> futures) {

        List<T> completedTasks = Lists.newArrayList();
        for (ListenableFuture<? extends T> future : futures) {
            // completed and has not been terminated
            if (future.isDone() && !future.isCancelled()) {
                T value = null;
                try {
                    value = MoreFutures.await(future, inMilliSeconds(10));
                } catch (MoreFuturesException moreFuturesException) {
                    log("filterCompleteTasks", moreFuturesException);
                }
                completedTasks.add(value);
            }
        }

        return completedTasks;
    }

    public static <T, EX extends Exception> T awaitOrThrow(ListenableFuture<T> future, Class<EX> toThrow) throws EX {

        try {
            return await(future, Duration.create());

        } catch (MoreFuturesException e) {
            EX instance = initException(toThrow, e);
            throw instance;
        }
    }

    private static <EX> EX initException(Class<EX> toThrow, Throwable sourceException) {

        Constructor[] allConstructors = toThrow.getDeclaredConstructors();
        for (Constructor constructor : allConstructors) {

            List<Object> constructorParameters = Lists.newArrayList();

            Class<?>[] parameterTypes = constructor.getParameterTypes();

            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterTypes[i].equals(String.class)) {
                    constructorParameters.add(sourceException.getMessage());

                } else if (parameterTypes[i].equals(Throwable.class)) {
                    constructorParameters.add(sourceException);
                }
            }

            try {
                return (EX) constructor.newInstance(constructorParameters.toArray());
            } catch (InstantiationException e) {
                continue;
            } catch (IllegalAccessException e) {
                continue;
            } catch (InvocationTargetException e) {
                continue;
            }
        }

        throw new IllegalArgumentException("Can't find constructor for " + toThrow);
    }

}