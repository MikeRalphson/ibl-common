package uk.co.bbc.iplayer.common.concurrency;

public final class MoreFutures3 {

    public static final Duration DEFAULT_DURATION = Duration.create();
    public static final boolean INTERRUPT_TASK = true;

    private MoreFutures3() {
        throw new AssertionError();
    }


    public static class FuturePiplineBuilder implements A, B {

        public static FuturePiplineBuilder createPipeline() {
            return new FuturePiplineBuilder();
        }


        @Override
        public A doAMethod() {
            return null;
        }

        @Override
        public B doBMethod() {
            return null;
        }

        @Override
        public BaseBuilder add() {
            return null;
        }

        @Override
        public Object build() {
            return null;
        }
    }

    interface BaseBuilder {
        BaseBuilder add();
        Object build();
    }

    interface A extends BaseBuilder {
        A doAMethod();
    }

    interface B extends BaseBuilder {
        B doBMethod();
    }
}
