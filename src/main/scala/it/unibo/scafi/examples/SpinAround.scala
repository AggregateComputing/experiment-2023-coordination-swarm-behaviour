package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.language.postfixOps

class SpinAround extends BaseMovement {
  override protected def movementLogic() = spinAround(mid() == 25).normalize
}
