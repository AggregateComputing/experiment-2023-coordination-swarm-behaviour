package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.language.postfixOps

class VFormationAndObstacle extends BaseMovement {
  def obstacle: Seq[Point3D] = {
    val deltaVector = Point3D(-200, 200, 0) - currentPosition()
    Seq(deltaVector)
  }
  override protected def movementLogic() = {
    val leader = mid() == 1
    val avoidObstacles = obstacleAvoidance(obstacle, 200, 50)
    val followerVelocity = rep(Point3D(-0.1, 0, 0)) { v =>
      val vShapeFormation =
        vShape(leader, v, 60, Math.PI / 2, 5, Point3D(-1, 0, 0)).normalize
      val avoidThemself = separation(v, OneHopNeighbourhoodWithinRange(40)).normalize
      val velocity = vShapeFormation + avoidThemself
      velocity
    } + avoidObstacles
    mux(leader)(followerVelocity / 5.0)(followerVelocity)
  }
}
