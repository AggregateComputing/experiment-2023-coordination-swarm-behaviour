package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp._

class LeaderBasedSeparation extends BaseMovement {
  val id = 24
  def range: java.lang.Double = sense("separation")
  override protected def movementLogic(): Point3D = {
    //val distances = OneHopNeighbourhoodWithinRange(range).query[Double, Double](0.0, 0.0, _ + nbrRange())
    //println(distances)
    rep(Point3D.Zero)(oldVelocity =>
      (sinkAt(mid() == id) * 0.05 + separation(oldVelocity, OneHopNeighbourhoodWithinRange(range))).normalize
    )
  }
}
