package examples.storehouse.actors

import groovy.transform.Immutable

@Immutable
class Product {
	String type
	String toString() {
		"a $type"
	}
}
