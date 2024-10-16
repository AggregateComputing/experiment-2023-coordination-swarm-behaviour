package it.unibo.scafi.evaluation

import it.unibo.scafi.examples.BaseMovement
import it.unibo.scafi.space.Point3D
class ConsensusCheck extends BaseMovement {
  val directions = List(
    Point3D(1, 1, 0),
    Point3D(1, -1, 0),
    Point3D(1, 0, 0),
    Point3D(0, 1, 0),
    Point3D(0, 0, 1),
    Point3D(-1, 0, 0),
    Point3D(0, -1, 0),
    Point3D(0, 0, -1),
    Point3D(-1, -1, 0),
    Point3D(-1, 1, 0),
  )

  def generateRandomPreferences(choices: Int): List[Double] = {
    val random = new scala.util.Random()
    val preferences = (0 until choices).map(_ => alchemistRandomGen.nextDouble()).toList
    softmaxNormalization(preferences)
  }

  lazy val localRandomPreferences = if(mid() != 1) generateRandomPreferences(directions.size) else 1.0 :: List.fill(directions.size - 1)(0.0)

  def softmaxNormalization(vector: List[Double]): List[Double] = {
    val exps = vector.map(math.exp)
    val sum = exps.sum
    exps.map(_ / sum)
  }
  override protected def movementLogic() = {

    val choice = consensusWithPreferences(localRandomPreferences,
      id => if(id == 1) 1 else 0.01 //_ => 0.01
    )
    node.put("choice", choice)
    directions(choice)
  }
}
