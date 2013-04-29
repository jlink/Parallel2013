package exercises.solution;

public interface Waiter {
    void finish();

    boolean waitUntilFinished(long timeout);
}
