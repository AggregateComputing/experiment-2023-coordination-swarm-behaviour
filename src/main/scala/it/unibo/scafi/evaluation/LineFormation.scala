package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D

class LineFormation extends BaseMovement {

  override protected def movementLogic(): Point3D = line(0 == mid(), 60, 5, Point3D.Zero)
}
