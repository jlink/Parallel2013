package exercise1;

// ganz schlimm!

public class WaitHelper {

    private boolean finished = false;

    public void finish() {
        finished = true;
    }

    public boolean waitMaxMillis(long timeout) {
        long start = System.currentTimeMillis();
        while (!finished && System.currentTimeMillis() - start < timeout) {
                Thread.yield();
        }
        return finished;
    }
}
