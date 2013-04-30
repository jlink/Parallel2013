package exercise2

class EventBus {

    private List<Subscriber> subscribers = []

    void subscribe(Subscriber subscriber) {
        subscribers << subscriber
    }

    void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber)
    }

    void publish(Subscriber sender, String event) {
        subscribers.each {subscriber ->
            if (subscriber == sender)
                return
            subscriber.receive(event)
        }
    }
}
