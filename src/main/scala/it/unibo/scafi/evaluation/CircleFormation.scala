package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D

class CircleFormation extends BaseMovement {

  val confidence = 5
  lazy val leaderId = sense[Integer]("leader")
  override protected def movementLogic() = {
    val lead = leaderId == mid()
    node.put("lead", lead)
    centeredCircle(leaderId == mid(), sense[java.lang.Double]("circle").doubleValue(), confidence, Point3D.Zero)
  }
}
