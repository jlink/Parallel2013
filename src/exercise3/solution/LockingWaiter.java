package exercise3.solution;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockingWaiter implements Waiter {

    private Lock lock = new ReentrantLock();
    private Condition finished = lock.newCondition();

    @Override
    public void finish() {
        lock.lock();
        try {
            finished.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean waitUntilFinished(long timeout) {
        lock.lock();
        try {
            return finished.await(timeout, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            return false;
        } finally {
            lock.unlock();
        }
    }

}
