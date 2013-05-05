package examples.storehouse.actors

import groovy.transform.Immutable
import groovyx.gpars.actor.DynamicDispatchActor

class ShelfActor extends DynamicDispatchActor {

    int capacity = 5
    private products = []

    void onMessage(PutIn message) {
        if (products.size() == capacity) {
            reply(new StorageError("cannot put in $message.product.type"))
            return
        }
        products << message.product
    }

    void onMessage(TakeOut message) {
        if (!products.remove(message.product)) {
            reply(new StorageError("cannot take out $message.product.type"))
            return
        }
        reply(message.product)
    }

    void onMessage(ListProducts message) {
        reply(productList())
    }

    private List productList() {
        Collections.unmodifiableList(products)
    }
}

@Immutable class PutIn { Product product }
@Immutable class TakeOut { Product product }
@Immutable class ListProducts {}
@Immutable class StorageError {String message}