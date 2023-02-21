package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class GridForming extends BaseMovement {
  override protected def movementLogic(): Point3D = {
    rep(Point3D(0, 0.1, 0))(v => {
      mux(mid() == 1)(v.rotate(0.0001).normalize / 10.0) {
        vShape(mid() == 1, v, 60, Math.PI / 2, 5)
      } + separation(v, OneHopNeighbourhoodWithinRange(40))
    })

  }
}
