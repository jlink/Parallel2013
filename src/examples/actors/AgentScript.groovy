package examples.actors

import groovyx.gpars.agent.Agent

def name = new Agent<List>( ['GPars'] )

name.send { it.add 'is safe!'  }
println name.val

name.send { updateValue it * 2 }
println name.val
