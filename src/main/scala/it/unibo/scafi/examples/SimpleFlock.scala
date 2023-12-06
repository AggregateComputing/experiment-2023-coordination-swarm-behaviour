package it.unibo.scafi.examples

import it.unibo.scafi.space.Point3D

class SimpleFlock extends BaseMovement {
  override protected def movementLogic(): Point3D =
    rep(brownian(2))(cuckerSmale(_, OneHopNeighbourhood, 4, 0.05))
}
