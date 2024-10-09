package it.unibo.alchemist.model.implementations.reactions

import it.unibo.alchemist.model.molecules.SimpleMolecule
import it.unibo.alchemist.model.nodes.GenericNode
import it.unibo.alchemist.model.{Environment, Position, TimeDistribution}
import org.apache.commons.math3.random.RandomGenerator

class DangerSpawn[T, P <: Position[P]](
    environment: Environment[T, P],
    distribution: TimeDistribution[T],
    randomGenerator: RandomGenerator,
    val max: Int,
    val min: Int
) extends AbstractGlobalReaction[T, P](environment, distribution) {
  override protected def executeBeforeUpdateDistribution(): Unit = {
    // create a random node and put it in the environment
    if (environment.getSimulation.getTime.toDouble < 3000) {
      val node = new GenericNode[T](environment)
      node.setConcentration(new SimpleMolecule("danger"), true.asInstanceOf[T])
      environment.addNode(
        node,
        environment.makePosition(
          min + randomGenerator.nextDouble() * (max - min),
          min + randomGenerator.nextDouble() * (max - min)
        )
      )
    }

  }
}
