package examples

import static groovyx.gpars.actor.Actors.*

def printer   = reactor { println it }
def decryptor = reactor { reply it.reverse() }

actor {
    decryptor << 'lellarap si yvoorG'
    react { answer ->
        printer << 'Decrypted message: ' + answer
        decryptor.stop()
        printer.stop()
    }
}.join()

printer.join()
decryptor.join()
