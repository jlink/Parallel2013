package examples.storehouse.actors

import groovyx.gpars.dataflow.DataflowQueue
import org.junit.After
import org.junit.Before
import org.junit.Test

import static groovyx.gpars.actor.Actors.actor

class MovingActorTest {

    MovingActor mover
    ShelfActor shelf1
    ShelfActor shelf2
    DataflowQueue replies
    Product book

    @Before
    void init() {
        mover = new MovingActor().start()
        shelf1 = new ShelfActor().start()
        shelf2 = new ShelfActor().start()
        book = new Product(type: 'book')
        replies = new DataflowQueue()
    }

    @After
    void release() {
        [mover, shelf1, shelf2].each { it.stop() }
        [mover, shelf1, shelf2].each { it.join() }
    }

    @Test
    void moveSuccessful() {
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
            shelf2 << new ListProducts()
            loop {
                react { answer ->
                    replies << answer
                }
            }
        }
        assert replies.val == []
        assert replies.val == [book]
    }

    @Test
    void moveFailsBecauseProductIsNotThere() {
        actor {
            mover << [product: book, from: shelf1, to: shelf2]
            react { answer ->
                replies << answer
            }
        }.join()
        assert replies.val == Result.Failure

        actor {
            shelf1 << new ListProducts()
            shelf2 << new ListProducts()
            loop(2) {
                react { answer ->
                    replies << answer
                }
            }
        }.join()

        assert replies.val == []
        assert replies.val == []
    }

    @Test
    void moveFailsBecauseTargetShelfIsFull() {
        shelf2.capacity = 0
        actor {
            shelf1 << new PutIn(book)
            mover << [product: book, from: shelf1, to: shelf2]
            react { answer ->
                replies << answer
            }
        }.join()
        assert replies.val == Result.Failure

        actor {
            shelf1 << new ListProducts()
            shelf2 << new ListProducts()
            loop(2) {
                react { answer ->
                    replies << answer
                }
            }
        }

        assert replies.val == [book]
        assert replies.val == []
    }
}
