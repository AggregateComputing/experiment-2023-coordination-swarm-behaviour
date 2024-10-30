package it.unibo.alchemist.model.actions

import it.unibo.alchemist.model.{Environment, Position2D, TimeDistribution}
import it.unibo.alchemist.model.implementations.reactions.AbstractGlobalReaction
import org.apache.commons.math3.random.RandomGenerator
import scala.util.Random
class RandomlyKillAgents[T, P <: Position2D[P]](
    environment: Environment[T, P],
    distribution: TimeDistribution[T],
    randomGenerator: RandomGenerator,
    killingPercentage: Double
) extends AbstractGlobalReaction[T, P](environment, distribution) {
  override protected def executeBeforeUpdateDistribution(): Unit = {
    val howManyKill = (killingPercentage * nodes.size).toInt
    val killable = managers.filterNot(_.get[Boolean]("lead"))
    val killing = new Random(randomGenerator.nextInt()).shuffle(killable).take(howManyKill)
    environment.getSimulation.schedule(() => killing.map(_.node).foreach(environment.removeNode))
  }
}
