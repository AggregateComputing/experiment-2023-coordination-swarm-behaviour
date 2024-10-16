package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

class VPatternFormation extends BaseMovement {
  lazy val id = sense[Integer]("leader")
  lazy val formationAngle = sense[java.lang.Double]("angleFactor")
  override protected def movementLogic() = {
    rep(Point3D.Zero)(vShape(id == mid(), _, 30, Math.PI / formationAngle, 5, Point3D(0.0, 0, 0))).normalize
  }
}
