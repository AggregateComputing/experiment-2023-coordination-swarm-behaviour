package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D

class FollowLeader extends BaseMovementNew {
  override protected def movementLogic(): Point3D =
    alignWithLeader(mid() == 0, explore(Point3D(0, 0, 0), Point3D(300, 300, 0), 1))
}
