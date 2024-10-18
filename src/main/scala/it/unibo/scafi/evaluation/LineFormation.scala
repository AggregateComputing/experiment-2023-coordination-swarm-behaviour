package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.{Actuation, BaseMovement}
import it.unibo.scafi.space.Point3D

class LineFormation extends BaseMovement {
  lazy val leaderId: Int = sense[Int]("leader")
  def distance: java.lang.Double = sense("distance")
  override protected def movementLogic(): Actuation = {
    val lead = leaderId == mid()
    node.put("lead", lead)
    line(lead, distance, 5, Point3D(0, 0.0, 0))
  }
}
