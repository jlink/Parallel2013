package exercises.solution;

import org.junit.Test;

public class WaitHelperTest {

    @Test
    public void waitTimeoutWithoutFinished() {
        def waiter = new LatchWaiter()
        def finished = waiter.waitMaxMillis(1000)
        assert !finished

        waiter = new LatchWaiter()
        Thread.start {
            waiter.finish();
        }
        finished = waiter.waitMaxMillis(1000)
        assert finished
    }

    @Test
    public void waitTimeoutWithFinished() {
        def waiter = new LatchWaiter()
        def finished = waiter.waitMaxMillis(1000)
        assert !finished

        waiter = new LatchWaiter()
        Thread.start {
            waiter.finish();
        }
        finished = waiter.waitMaxMillis(1000)
        assert finished
    }

}
