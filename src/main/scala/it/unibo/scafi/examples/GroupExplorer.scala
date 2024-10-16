package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.language.postfixOps

class GroupExplorer extends BaseMovement {
  override protected def movementLogic() = {
    val collisionStrength = 10
    val towardsLeaderStrength = 3
    val followLeaderVelocityStrength = 2
    val upperBound = Point3D(1000, 1000, 0)
    val collisionRange = 30
    rep(Point3D.Zero) { oldVelocity =>
      val center = mid() == 1
      val towardsLeader = maintainUntil(sinkAt(center))(isTeamFormed(center, 40))
      val avoidCollision = separation(oldVelocity, OneHopNeighbourhoodWithinRange(collisionRange))
      val followLeaderVelocity = alignWithLeader(center, explore(Point3D.Zero, upperBound, maxVelocity = 1))
      (towardsLeader * towardsLeaderStrength + avoidCollision * collisionStrength + followLeaderVelocity * followLeaderVelocityStrength).normalize
    }
  }
}
