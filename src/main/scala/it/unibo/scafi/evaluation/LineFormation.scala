package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D

class LineFormation extends BaseMovement {
  val id = 24
  override protected def movementLogic(): Point3D = line(id == mid(), 20, 5, Point3D.Zero)
}
