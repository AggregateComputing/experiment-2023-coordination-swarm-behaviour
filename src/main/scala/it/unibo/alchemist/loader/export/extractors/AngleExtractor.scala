package it.unibo.alchemist.loader.`export`.extractors

import it.unibo.alchemist.loader.`export`.Extractor
import it.unibo.alchemist.model.interfaces
import it.unibo.alchemist.model.interfaces.{Actionable, Environment, Position2D}

import java.util
import scala.jdk.CollectionConverters.ListHasAsScala

class AngleExtractor(val centerId: Int) extends Extractor[Double] {
  override def getColumnNames: util.List[String] = util.List.of("angle")

  override def extractData[T](
      environment: Environment[T, _],
      actionable: Actionable[T],
      time: interfaces.Time,
      l: Long
  ): util.Map[String, Double] = {
    type EnvironmentType = Environment[T, Position2D[_]]
    val typeEnvironment = environment.asInstanceOf[EnvironmentType]
    val center = typeEnvironment.getNodeByID(centerId)
    val others = typeEnvironment.getNodes.asScala.filterNot(_ == center)
    val positions = others.map(node => typeEnvironment.getPosition(node))
    val centerPosition = typeEnvironment.getPosition(center)
    val angles = positions.map(position => {
      val x = position.getX - centerPosition.getX
      val y = position.getY - centerPosition.getY
      Math.atan2(y, x)
    })
    val (left, right) = angles.map(Math.toDegrees).map(_ * -1).span(_ < 90)
    java.util.Map.of("angle", (left ++ right.map(180 - _)).sum / angles.size)
  }
}
