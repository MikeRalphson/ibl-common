package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.ListenableFuture;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IdentifyingFuture<V> extends ForwardingListenableFuture<V> {

    private final String descriptor;

    public IdentifyingFuture(ListenableFuture<V> delegate, String descriptor) {
        super(delegate);
        this.descriptor = descriptor;
    }

    public String getDescriptor() {
        return descriptor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("descriptor", descriptor)
                .toString();
    }
}
