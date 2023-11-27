package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D

import scala.language.postfixOps

class BranchingExample extends BaseMovementNew {
  lazy val size = alchemistEnvironment.getNodeCount / 2.0
  override protected def movementLogic(): Point3D = {
    def logic = alignWithLeader(mid() == 1 || mid() == size + 1, remember(brownian(2)))
    branch(mid() < size)(logic)(logic)
  }
}
