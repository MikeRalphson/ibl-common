package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.util.concurrent.ListenableFuture;
import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

public class FutureContextDSL {

    public static FutureContextDSL createFuturePipeline() {
        return null;
    }

    public FutureContextDSL add(ListenableFuture future) {
        return null;
    }

    public FutureContextDSL onException() {
        return null;
    }

    public ExecutingFutureContextDSL execute() {
        return null;
    }

    public FutureContextDSL foreach() {
        return null;
    }

    public PipeableFuture build() {
        return null;
    }

    class ExecutingFutureContextDSL extends FutureContextDSL {

        private FutureContextDSL futureContextDSL;

        public ExecutingFutureContextDSL(FutureContextDSL futureContextDSL) {
            this.futureContextDSL = futureContextDSL;
        }

        public ExecutingFutureContextDSL execute() {
            return null;
        }

        public ExecutingFutureContextDSL foreach(ThrowableFunction f) {
            return null;
        }
    }
}
