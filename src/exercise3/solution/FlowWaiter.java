package exercise3.solution;

import groovyx.gpars.dataflow.DataflowVariable;

import java.util.concurrent.TimeUnit;

public class FlowWaiter implements Waiter {

    private final DataflowVariable<Boolean> finished = new DataflowVariable<Boolean>();

    public void finish() {
        finished.bind(true);
    }

    public boolean waitUntilFinished(long timeoutMilliseconds) {
        try {
            Boolean result = finished.getVal(timeoutMilliseconds, TimeUnit.MILLISECONDS);
            return result == null ? false : true;
        } catch (InterruptedException ie) {
            return false;
        }

    }
}
