package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp._

class LeaderBasedSeparation extends BaseMovement {
  lazy val leaderId = sense[Int]("leader")
  def range: java.lang.Double = sense("separation")
  override protected def movementLogic() = {
    val lead = leaderId == mid()
    node.put("lead", lead)
    val distances = OneHopNeighbourhoodNearestN(4).queryNeighborhood[Double](0.0, _ + nbrRange())
    val others = distances.filter(_._1 != mid)
    val averageDistance = others.values.sum / others.size
    node.put("distance", averageDistance)
    rep(Point3D.Zero)(oldVelocity =>
      (sinkAt(lead) * 0.05 + separation(oldVelocity, OneHopNeighbourhoodWithinRange(range))).normalize
    )
  }
}
