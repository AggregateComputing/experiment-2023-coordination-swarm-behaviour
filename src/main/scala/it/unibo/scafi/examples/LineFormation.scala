package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.language.postfixOps

class LineFormation extends BaseMovementNew {
  override protected def movementLogic(): Point3D = {
    val leader = mid() == 1
    val targetDistance = 40
    val confidence = 5
    val lineFormingConfidence = 10
    val shapeWeightWhenAligned = 0.9
    rep(Point3D.Zero) { oldVelocity =>
      val shape = line(leader, targetDistance, confidence)
      val lineFormed = isTeamFormed(leader, targetDistance + lineFormingConfidence)
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
