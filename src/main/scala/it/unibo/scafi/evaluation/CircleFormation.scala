package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D

class CircleFormation extends BaseMovement {

  val confidence = 5
  val id = 24
  override protected def movementLogic() = centeredCircle(id == mid(), sense[java.lang.Double]("circle").doubleValue(), confidence, Point3D.Zero)
}
