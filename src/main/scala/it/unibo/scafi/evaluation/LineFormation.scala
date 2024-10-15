package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D

class LineFormation extends BaseMovement {
  lazy val id: Int = sense[Int]("leader")
  val breakingLeader = 1000
  def distance: java.lang.Double = sense("distance")
  override protected def movementLogic(): Point3D = {
    branch(id == mid())(node.put("speed", 0.5)) {}
    val leader = branch(T(breakingLeader) != 0)(id == mid())(S(1000, nbrRange))
    line(leader, distance, 5, Point3D(0, 1, 0))
  }
}
