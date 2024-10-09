package it.unibo.alchemist.loader.`export`.extractors

import it.unibo.alchemist.boundary.Extractor
import it.unibo.alchemist.model.{Actionable, Environment, Position2D, Time}

import java.util
import scala.jdk.CollectionConverters.ListHasAsScala

class AngleExtractor(val centerId: Int) extends Extractor[Double] {
  override def getColumnNames: util.List[String] = util.List.of("angle[mean]", "angle[std]")
  override def extractData[T](
      environment: Environment[T, _],
      actionable: Actionable[T],
      time: Time,
      l: Long
  ): util.Map[String, Double] = {
    type EnvironmentType = Environment[T, Position2D[_]]
    val typeEnvironment = environment.asInstanceOf[EnvironmentType]
    val center = typeEnvironment.getNodeByID(centerId)
    val others = typeEnvironment.getNodes.asScala.filterNot(_ == center)
    val positions = others.map(node => typeEnvironment.getPosition(node))
    val centerPosition = typeEnvironment.getPosition(center)
    val angles = positions.map { position =>
      val x = position.getX - centerPosition.getX
      val y = position.getY - centerPosition.getY
      Math.atan2(y, x)
    }
    val (left, right) = angles.map(Math.toDegrees).map(_ * -1).span(_ < 90)
    val fixedAngles = left ++ right.map(180 - _)
    val mean = fixedAngles.sum / angles.size
    val std = Math.sqrt(fixedAngles.map(a => Math.pow(a - mean, 2)).sum / angles.size)
    java.util.Map.of("angle[mean]", mean, "angle[std]", std)
  }
}
