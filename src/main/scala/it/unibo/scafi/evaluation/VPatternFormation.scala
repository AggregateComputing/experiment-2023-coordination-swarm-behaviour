package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

class VPatternFormation extends BaseMovement {
  override protected def movementLogic(): Point3D =
    rep(Point3D.Zero)(vShape(0 == mid(), _, 60, Math.PI / 2, 10, Point3D(0.0, 0, 0))).normalize
}
