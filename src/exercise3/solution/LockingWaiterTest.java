package exercise3.solution;

public class LockingWaiterTest extends AbstractWaiterTest {

    @Override
    protected Waiter createWaiter() {
        return new LockingWaiter();
    }

}
