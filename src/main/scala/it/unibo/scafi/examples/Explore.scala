package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D

class Explore extends BaseMovementNew {
  override protected def movementLogic(): Point3D = explore(Point3D(0, 0, 0), Point3D(1000, 1000, 0), 1)
}
