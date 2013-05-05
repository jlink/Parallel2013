package examples.storehouse.actors

import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.DefaultActor

class MovingActor extends DefaultActor {
    void act() {
        react { message ->
            move(message.product, message.from, message.to)
        }
    }

    private move(product, Actor from, Actor to) {
        def takenOut = from.sendAndWait(new TakeOut(product))
        if (takenOut instanceof StorageError) {
            reply Result.Failure
            return
        }
        to.send(new PutIn(product))
        reply Result.Success
    }

}

enum Result {
    Success, Failure
}
