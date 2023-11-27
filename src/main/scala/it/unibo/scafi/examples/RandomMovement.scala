package it.unibo.scafi.examples
import it.unibo.scafi.space.Point3D
import it.unibo.scafi.space.pimp.PimpPoint3D

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class RandomMovement extends BaseMovementNew {
  override protected def movementLogic(): Point3D = maintainTrajectory(brownian().normalize)(100 seconds)
}
