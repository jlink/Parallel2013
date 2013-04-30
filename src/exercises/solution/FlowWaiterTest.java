package exercises.solution;

public class FlowWaiterTest extends AbstractWaiterTest {

    @Override
    protected Waiter createWaiter() {
        return new FlowWaiter();
    }

}
