package exercise3.solution;

public class GroovyFlowWaiterTest extends AbstractWaiterTest {

    @Override
    protected Waiter createWaiter() {
        return new GroovyFlowWaiter();
    }

}
