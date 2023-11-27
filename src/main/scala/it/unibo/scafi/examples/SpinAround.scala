package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.language.postfixOps

class SpinAround extends BaseMovementNew {
  override protected def movementLogic(): Point3D = spinAround(mid() == 25).normalize
}
