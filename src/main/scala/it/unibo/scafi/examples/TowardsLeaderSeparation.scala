package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp._

import scala.language.postfixOps

class TowardsLeaderSeparation extends BaseMovementNew {
  override protected def movementLogic(): Point3D =
    rep(Point3D.Zero)(oldVelocity =>
      (sinkAt(mid() == 1) + separation(oldVelocity, OneHopNeighbourhoodWithinRange(30)) * 2).normalize
    )
}
