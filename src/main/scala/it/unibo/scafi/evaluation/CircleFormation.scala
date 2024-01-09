package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D

class CircleFormation extends BaseMovement {

  val confidence = 5
  val id = 0
  override protected def movementLogic(): Point3D = centeredCircle(id == mid(), sense[Integer]("circle").doubleValue(), confidence, Point3D.Zero)
}
