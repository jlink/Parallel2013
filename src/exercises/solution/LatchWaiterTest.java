package exercises.solution;

public class LatchWaiterTest extends AbstractWaiterTest {

    @Override
    protected Waiter createWaiter() {
        return new LatchWaiter();
    }

}
