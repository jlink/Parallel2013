package exercise3.solution

import groovyx.gpars.dataflow.DataflowVariable

import java.util.concurrent.TimeUnit

public class FlowWaiter implements Waiter {

    private final finished = new DataflowVariable<Boolean>()

    public void finish() {
        finished << true
    }

    public boolean waitUntilFinished(long timeoutMilliseconds) {
        try {
            return finished.getVal(timeoutMilliseconds, TimeUnit.MILLISECONDS)
        } catch (InterruptedException ie) {
            return false
        }
    }
}
