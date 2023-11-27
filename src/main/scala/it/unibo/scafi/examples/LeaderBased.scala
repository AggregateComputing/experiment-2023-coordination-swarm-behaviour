package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D

import scala.language.postfixOps

class LeaderBased extends BaseMovementNew {
  override protected def movementLogic(): Point3D =
    alignWithLeader(mid() == 1, remember(brownian(2)))
}
