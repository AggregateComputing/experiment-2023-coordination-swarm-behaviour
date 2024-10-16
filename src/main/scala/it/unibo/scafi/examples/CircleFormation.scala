package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.language.postfixOps

class CircleFormation extends BaseMovement {
  override protected def movementLogic() = {
    val leader = mid() == 1
    val targetRange = 150
    val confidence = 5
    val shapeWeightWhenAligned = 0.9
    rep(Point3D.Zero) { oldVelocity =>
      val shape = centeredCircle(leader, targetRange, confidence)
      val lineFormed = isCircleFormed(leader, targetRange, confidence)
      (mux(lineFormed) {
        alignWithLeader(
          mid() == 1,
          explore(Point3D.Zero, Point3D(1000, 1000, 0), maxVelocity = 1)
        ) + shape * shapeWeightWhenAligned
      } {
        shape
      } + separation(oldVelocity, OneHopNeighbourhoodWithinRange(30))).normalize
    } * 2
  }
}
