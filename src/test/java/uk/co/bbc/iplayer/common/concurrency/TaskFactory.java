package uk.co.bbc.iplayer.common.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public final class TaskFactory {

    private TaskFactory() {
        throw new AssertionError();
    }

    public static Callable<String> createTask(final String returnStr) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return returnStr;
            }
        };
    }

    public static <T extends Exception> Callable<String> createThatThrows(final T toThrow) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                throw toThrow;
            }
        };
    }

    public static Callable<String> createTimedTask(final String returnStr, final Duration duration) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.MILLISECONDS.sleep(duration.getLength());
                return createTask(returnStr).call();
            }
        };
    }

    public static Runnable createNoOpRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                // do nothing
            }
        };
    }
}
