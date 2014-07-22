package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.ListenableFuture;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IdentifyingFuture<V> extends ForwardingListenableFuture<V> {

    private final String descriptor;
    private final String statsDescriptor;

    public IdentifyingFuture(ListenableFuture<V> delegate, String descriptor, String statsDescriptor) {
        super(delegate);
        this.descriptor = descriptor;
        this.statsDescriptor = statsDescriptor;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public String getStatsDescriptor() {
        return statsDescriptor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("descriptor", descriptor)
                .toString();
    }
}
