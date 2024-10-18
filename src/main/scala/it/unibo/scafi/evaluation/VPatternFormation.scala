package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

class VPatternFormation extends BaseMovement {
  lazy val leaderId = sense[Integer]("leader")
  lazy val formationAngle = sense[java.lang.Double]("angleFactor")
  override protected def movementLogic() = {
    val lead = leaderId == mid()
    node.put("lead", lead)
    rep(Point3D.Zero)(vShape(lead, _, 30, Math.PI / formationAngle, 5, Point3D(0.0, 0, 0))).normalize
  }
}
