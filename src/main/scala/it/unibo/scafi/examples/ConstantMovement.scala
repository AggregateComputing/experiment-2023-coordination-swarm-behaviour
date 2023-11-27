package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D

class ConstantMovement extends BaseMovementNew {
  override protected def movementLogic(): Point3D = Point3D(1, 0, 0)
}
