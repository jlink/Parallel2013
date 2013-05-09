package examples.storehouse.stm

import groovy.transform.Immutable

@Immutable
class Product {
	String type
	String toString() {
		"a $type"
	}
}
