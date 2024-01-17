package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D

class LineFormation extends BaseMovement {
  val id = 24
  def distance: java.lang.Double = sense("distance")
  override protected def movementLogic(): Point3D = line(id == mid(), distance, 5, Point3D.Zero)
}
