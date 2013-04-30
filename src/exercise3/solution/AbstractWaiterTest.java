package exercise3.solution;

import groovyx.gpars.dataflow.DataflowVariable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class AbstractWaiterTest {

    protected Waiter waiter;

    @Before
    public void init() {
        waiter = createWaiter();
    }

    protected abstract Waiter createWaiter();

    @Test
    public void waitUntilFinished() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                waiter.finish();
            }
        }).start();
        boolean finished = waiter.waitUntilFinished(Long.MAX_VALUE);
        assertTrue(finished);
    }

    @Test
    public void waitUntilFinishedWithTimeout() {
        final int SHORT_WAIT = 100;
        boolean finished = waiter.waitUntilFinished(SHORT_WAIT);
        assertFalse(finished);
    }

    @Test
    public void waitUntilFinishedWithInterrupt() throws Throwable {
        final DataflowVariable<Boolean> finished = new DataflowVariable<Boolean>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                finished.bind(waiter.waitUntilFinished(Long.MAX_VALUE));
            }
        });
        thread.start();
        thread.interrupt();
        assertFalse(finished.get().booleanValue());
    }
}
