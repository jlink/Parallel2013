package examples.storehouse.actors

import groovyx.gpars.dataflow.DataflowQueue
import org.junit.After
import org.junit.Before
import org.junit.Test

import java.util.concurrent.TimeUnit

import static groovyx.gpars.actor.Actors.actor

class MovingActorTest {

    MovingActor mover
    ShelfActor shelf1
    ShelfActor shelf2
    DataflowQueue replies = new DataflowQueue()

    @Before
    void init() {
        mover = new MovingActor().start()
        shelf1 = new ShelfActor().start()
        shelf2 = new ShelfActor().start()
    }

    @After
    void release() {
        [mover, shelf1, shelf2].each { it.stop() }
        [mover, shelf1, shelf2].each { it.join() }
    }

    @Test
    void moveSuccessful() {
        def book = new Product(type: 'book')
        actor {
            shelf1 << new PutIn(book)
            mover << [product: book, from: shelf1, to: shelf2]
            react { answer ->
                replies << answer
            }
        }.join()
        assert replies.val == Result.Success

        actor {
            shelf1 << new ListProducts()
            react { answer ->
                replies << answer
            }
        }.join()
        assert replies.val == []

        actor {
            shelf2 << new ListProducts()
            react { answer ->
                replies << answer
            }
        }.join()
        assert replies.val == [book]
    }

    @Test
    void moveFailsBecauseProductIsNotThere() {
        assert false
    }

    @Test
    void moveFailsBecauseTargetShelfIsFull() {
        assert false
    }
}
