package exercise3.solution;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LatchWaiter implements Waiter {

    private final CountDownLatch finished = new CountDownLatch(1);

    @Override
    public void finish() {
        finished.countDown();
    }

    @Override
    public boolean waitUntilFinished(long timeout) {
        try {
            return finished.await(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }
}
