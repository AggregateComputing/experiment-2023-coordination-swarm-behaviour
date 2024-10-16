package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class AllShape extends BaseMovement {
  val (linePattern, circlePattern, vPattern, cohesionPattern) = (0, 20, 40, 45)
  def source = List(linePattern, circlePattern, vPattern, cohesionPattern).contains(mid())
  override protected def movementLogic(): Actuation = {
    val lead: Int = GWithShare[Int](source, mid, identity)
    def leaderPolicy = maintainTrajectory(brownian())(1000 seconds) / 2.0
    align(lead) {
      case `linePattern` =>
        line(lead == mid(), 40, 5, leaderPolicy)
      case `circlePattern` =>
        centeredCircle(lead == mid(), 150, 5, leaderPolicy)
      case `vPattern` =>
        rep(Point3D.Zero)(vShape(lead == mid(), _, 60, Math.PI / 2, 5, leaderPolicy))
          .withSpeed(mux(lead == mid())(0.5)(1))
      case `cohesionPattern` =>
        rep(Point3D.Zero) { oldVelocity =>
          val center = lead == mid()
          val towardsLeader = maintainUntil(sinkAt(center))(isTeamFormed(center, 40))
          val avoidCollision = separation(oldVelocity, OneHopNeighbourhoodWithinRange(30))
          val followLeaderVelocity = alignWithLeader(center, maintainTrajectory(brownian())(300 seconds))
          (towardsLeader * 2 + avoidCollision * 4 + followLeaderVelocity * 2).normalize
        }
      case _ => Point3D.Zero
    }
  }
}
