package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.{Actuation, BaseMovement}
import it.unibo.scafi.space.Point3D

class LineFormation extends BaseMovement {
  lazy val id: Int = sense[Int]("leader")
  val breakingLeader = 1000
  def distance: java.lang.Double = sense("distance")
  override protected def movementLogic(): Actuation = {
    val leader = branch(T(breakingLeader) != 0)(id == mid())(S(1000, nbrRange))
    line(leader, distance, 5, Point3D(0, 0.5, 0))
  }
}
