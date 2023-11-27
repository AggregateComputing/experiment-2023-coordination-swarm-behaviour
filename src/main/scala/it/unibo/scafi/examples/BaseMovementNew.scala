package it.unibo.scafi.examples

import it.unibo.alchemist.model.interfaces.Position
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist.ScafiAlchemistSupport
import it.unibo.scafi.macroswarm.MacroSwarmAlchemistSupport._
import it.unibo.scafi.macroswarm.MacroSwarmAlchemistSupport.incarnation._
import it.unibo.scafi.space.Point3D
trait BaseMovementNew
    extends AggregateProgram
    with BaseMovementLib
    with StandardSensors
    with ScafiAlchemistSupport
    with FieldUtils
    with TimeUtils
    with GPSMovement
    with FlockLib
    with CustomSpawn
    with BlocksWithGC
    with BlocksWithShare
    with ProcessFix
    with LeaderBasedLib
    with PlanMovementLib
    with BlockS
    with PatternFormationLib
    with TeamFormationLib {

  override def main(): Any = actuate(movementLogic())

  protected def movementLogic(): Point3D

  protected def actuate(velocity: Point3D): Unit = {
    val previousPosition = alchemistEnvironment.getPosition(alchemistEnvironment.getNodeByID(mid()))
    val target = previousPosition.plus(Array(velocity.x, velocity.y))
    target.asInstanceOf[Position[_]]
    node.put("velocity", velocity)
    node.put("destination", target)
  }
}
