package exercise3.solution;

public class FlowWaiterTest extends AbstractWaiterTest {

    @Override
    protected Waiter createWaiter() {
        return new FlowWaiter();
    }

}
