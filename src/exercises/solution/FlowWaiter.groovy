package exercises.solution

import groovyx.gpars.dataflow.DataflowVariable;
import java.util.concurrent.TimeUnit;

public class FlowWaiter implements Waiter {

    private final finished = new DataflowVariable<Boolean>();

    public void finish() {
        finished << true
    }

    public boolean waitUntilFinished(long timeout) {
        finished.getVal(timeout, TimeUnit.MILLISECONDS) ?: false
    }
}
