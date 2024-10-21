package it.unibo.scafi.examples

import it.unibo.alchemist.model.{Environment, Position, Position2D}
import it.unibo.alchemist.model.scafi.ScafiIncarnationForAlchemist.ScafiAlchemistSupport
import it.unibo.scafi.macroswarm.MacroSwarmAlchemistSupport._
import it.unibo.scafi.macroswarm.MacroSwarmAlchemistSupport.incarnation._
import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.jdk.CollectionConverters.ListHasAsScala

trait Actuation
case class MoveTo(destination: Point3D) extends Actuation {
  def withSpeed(speed: Double): MoveToWithSpeed = MoveToWithSpeed(destination, speed)
}
case class MoveToWithSpeed(destination: Point3D, speed: Double) extends Actuation
trait BaseMovement
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
    with ConsensusLib
    with PatternFormationLib
    with TeamFormationLib {

  private val MAX_MAGNITUDE = 10.0
  private val SPEED_KEY = 0.8
  private val MAX_SPEED = 1.4
  override def main(): Any = actuate(movementLogic())

  protected def movementLogic(): Actuation
  implicit def toActuation(p: Point3D): Actuation = MoveTo(p)
  implicit class RichPoint3DToActuation(p: Point3D) {
    def withSpeed(speed: Double): Actuation = MoveToWithSpeed(p, speed)
  }

  protected def actuate(actuation: Actuation): Unit = {
    val velocity = actuation match {
      case MoveTo(destination) => destination
      case MoveToWithSpeed(destination, _) => destination
    }
    val previousPosition = alchemistEnvironment.getPosition(alchemistEnvironment.getNodeByID(mid()))

    val direction = velocity.normalize
    val adjustedDirection = if (direction.x.isNaN || direction.y.isNaN) Point3D(0.01, 0.01, 0) else direction
    val target = previousPosition.plus(Array(adjustedDirection.x * MAX_MAGNITUDE, adjustedDirection.y * MAX_MAGNITUDE))
    val speed = actuation match {
      case MoveToWithSpeed(_, speed) => speed
      case _ =>
        velocity.distance(Point3D.Zero) * SPEED_KEY match {
          case s if s > MAX_SPEED => MAX_SPEED
          case s => s
        }
    }
    target.asInstanceOf[Position[_]]
    node.put("speed", speed)
    node.put("velocity", adjustedDirection)
    node.put("destination", target)
  }
  override def log(key: String, data: Any): Unit =
    node.put(key, data)

  override def allPosition(center: Int): List[(Int, Point3D)] = {
    type TypedEnvironment = Environment[Any, Position2D[_]]
    val typed = alchemistEnvironment.asInstanceOf[TypedEnvironment]
    val nodes = typed.getNodes.asScala
    val center = typed.getPosition(alchemistEnvironment.getNodeByID(mid()))
    nodes
      .map(node => node.getId -> typed.getPosition(node))
      .map(pair => pair._1 -> Point3D(center.getX - pair._2.getX, center.getY - pair._2.getY, 0))
      .toList
      .filter(_._1 != mid())
      .sortBy(_._1)
  }
}
