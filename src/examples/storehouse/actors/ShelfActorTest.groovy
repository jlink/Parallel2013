package examples.storehouse.actors

import groovyx.gpars.dataflow.DataflowQueue
import org.junit.After
import org.junit.Before
import org.junit.Test

import static groovyx.gpars.actor.Actors.actor

class ShelfActorTest {

    ShelfActor shelf
    DataflowQueue replies

    @Before
    void init() {
        shelf = new ShelfActor(capacity: 5).start()
        replies = new DataflowQueue()
    }

    @After
    void release() {
        shelf.stop()
        shelf.join()
    }

    @Test
    void putInProducts() throws Exception {

        def book1 = new Product(type: 'book1')
        def book2 = new Product(type: 'book2')

        actor {
            shelf << new PutIn(book1)
            shelf << new PutIn(book2)
            shelf << new ListProducts()
            react { answer ->
                replies << answer
            }
        }.join()

        assert replies.val == [book1, book2]
    }

    @Test
    void putInProductsOverCapacity() throws Exception {

        actor {
            ['book1', 'book2', 'book3', 'book4', 'book5', 'book6', 'book7'].each {
                shelf << new PutIn(new Product(type: it))
            }
            shelf << new ListProducts()
            loop(3) {
                react { answer ->
                    replies << answer
                }
            }
        }.join()

        assert replies.val == new StorageError('cannot put in book6')
        assert replies.val == new StorageError('cannot put in book7')
        assert replies.val*.type == ['book1', 'book2', 'book3', 'book4', 'book5']

    }

    @Test
    void takeOutProduct() throws Exception {

        def book1 = new Product(type: 'book1')
        def book2 = new Product(type: 'book2')

        actor {
            shelf << new PutIn(book1)
            shelf << new PutIn(book2)
            shelf << new TakeOut(book1)
            shelf << new ListProducts()
            loop(2) {
                react { answer ->
                    replies << answer
                }
            }
        }.join()

        assert replies.val == book1
        assert replies.val == [book2]
    }

    @Test
    void takingOutMissingProductFails() throws Exception {

        def book1 = new Product(type: 'book1')
        def book2 = new Product(type: 'book2')

        def answers = new DataflowQueue()

        actor {
            shelf << new PutIn(book1)
            shelf << new TakeOut(book2)
            shelf << new ListProducts()
            loop(2) {
                react { answer ->
                    answers << answer
                }
            }
        }.join()

        assert answers.val == new StorageError('cannot take out book2')
        assert answers.val == [book1]
    }
}
