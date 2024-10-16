package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp._

class LeaderBasedSeparation extends BaseMovement {
  val id = 24
  def range: java.lang.Double = sense("separation")
  override protected def movementLogic() = {
    val distances = OneHopNeighbourhoodNearestN(4).queryNeighborhood[Double](0.0, _ + nbrRange())
    val others = distances.filter(_._1 != mid)
    val averageDistance = others.values.sum / others.size
    node.put("distance", averageDistance)
    rep(Point3D.Zero)(oldVelocity =>
      (sinkAt(mid() == id) * 0.05 + separation(oldVelocity, OneHopNeighbourhoodWithinRange(range))).normalize
    )
  }
}
