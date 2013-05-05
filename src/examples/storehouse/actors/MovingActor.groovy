package examples.storehouse.actors

import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.DefaultActor

import java.util.concurrent.TimeUnit

class MovingActor extends DefaultActor {
    void act() {
        react { message ->
            move(message.product, message.from, message.to, sender)
        }
    }

    private move(product, Actor from, Actor to, localSender) {
        def takenOut = from.sendAndWait(new TakeOut(product))
        if (takenOut instanceof StorageError) {
            reply Result.Failure
            return
        }
        to.send(new PutIn(product))
        react(1, TimeUnit.SECONDS) { message ->
            if (message instanceof StorageError) {
                from << new PutIn(product)
                localSender << Result.Failure
            } else {
                localSender << Result.Success
            }
        }
    }

}

enum Result {
    Success, Failure
}
