package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class VFormation extends BaseMovement {
  override protected def movementLogic() = {
    rep(Point3D(0, 0.1, 0))(v =>
      {
        vShape(mid() == 1, v, 60, Math.PI / 2, 5, v.rotate(0.0001).normalize / 10.0)
      } + separation(v, OneHopNeighbourhoodWithinRange(5))
    )
  }
}
