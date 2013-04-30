package exercise2

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class EventBusTest {

    def bus

    @Before
    public void init() {
        bus = new EventBus()
    }

    @Test
    public void singleSubscriberReceivesPublishedEventUntilUnsubscribed() {
        def sender = {} as Subscriber
        def received
        def receiver = {received = it} as Subscriber

        bus.subscribe(receiver)
        bus.publish(sender, "event 1")
        assert received == "event 1"
        bus.publish(sender, "event 2")
        assert received == "event 2"

        bus.unsubscribe(receiver)
        bus.publish(sender, "event 3")
        assert received == "event 2"
    }

    @Test
    public void multipleSubscribersAllReceivePublishedEvents() {
        def sender = {} as Subscriber
        def received = []
        def receiver1 = {received << "r1"} as Subscriber
        def receiver2 = {received << "r2"} as Subscriber
        bus.subscribe(receiver1)
        bus.subscribe(receiver2)

        bus.publish(sender, "event")
        assert received == ["r1", "r2"]
    }

    @Test
    public void senderDoesNotReceiveEventsPublishedByItself() {
        def sender = { Assert.fail("Sender should not receive events")} as Subscriber
        bus.subscribe(sender)
        bus.publish(sender, "event")
    }
}
