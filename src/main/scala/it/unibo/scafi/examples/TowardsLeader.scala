package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D

import scala.language.postfixOps

class TowardsLeader extends BaseMovement {
  override protected def movementLogic(): Point3D =
    sinkAt(mid() == 1)
}
