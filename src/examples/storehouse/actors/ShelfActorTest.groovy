package examples.storehouse.actors

import groovyx.gpars.dataflow.DataflowQueue
import groovyx.gpars.dataflow.DataflowVariable
import groovyx.gpars.dataflow.stream.DataflowStream
import org.junit.After
import org.junit.Before
import org.junit.Test

import java.util.concurrent.TimeUnit

import static groovyx.gpars.actor.Actors.actor

class ShelfActorTest {

    ShelfActor shelf

    @Before
    void init() {
        shelf = new ShelfActor(capacity: 5).start()
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

        def products = new DataflowVariable()

        actor {
            shelf << new PutIn(book1)
            shelf << new PutIn(book2)
            shelf << new ListProducts()
            react { answer ->
                products << answer
            }
        }

        assert products.val == [book1, book2]
    }

    @Test
    void putInProductsOverCapacity() throws Exception {

        def answers = new DataflowQueue()

        actor {
            shelf << new PutIn(new Product(type: 'book1'))
            shelf << new PutIn(new Product(type: 'book2'))
            shelf << new PutIn(new Product(type: 'book3'))
            shelf << new PutIn(new Product(type: 'book4'))
            shelf << new PutIn(new Product(type: 'book5'))
            shelf << new PutIn(new Product(type: 'book6'))
            shelf << new PutIn(new Product(type: 'book7'))
            shelf << new ListProducts()
            loop {
                react { answer ->
                    answers << answer
                }
            }
        }

        assert answers.val == new StorageError('cannot put in book6')
        assert answers.val == new StorageError('cannot put in book7')
        assert answers.val*.type == ['book1', 'book2', 'book3', 'book4', 'book5']

    }

    @Test
    void takeOutProduct() throws Exception {

        def book1 = new Product(type: 'book1')
        def book2 = new Product(type: 'book2')

        def answers = new DataflowQueue()

        actor {
            shelf << new PutIn(book1)
            shelf << new PutIn(book2)
            shelf << new TakeOut(book1)
            shelf << new ListProducts()
            loop {
                react { answer ->
                    answers << answer
                }
            }
        }

        assert answers.val == book1
        assert answers.val == [book2]
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
            loop {
                react { answer ->
                    answers << answer
                }
            }
        }

        assert answers.val == new StorageError('cannot take out book2')
        assert answers.val == [book1]
	}
}
