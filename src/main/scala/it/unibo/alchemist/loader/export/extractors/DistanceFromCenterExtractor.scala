package it.unibo.alchemist.loader.`export`.extractors

import it.unibo.alchemist.boundary.Extractor
import it.unibo.alchemist.model
import it.unibo.alchemist.model.{Actionable, Environment, Position2D, Time}

import java.util
import scala.jdk.CollectionConverters.CollectionHasAsScala

class DistanceFromCenterExtractor(val centerId: Int) extends Extractor[Double] {
  override def getColumnNames: util.List[String] = util.List.of("distance[mean]", "distance[std]")

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
    val distances = others.map(other => environment.getDistanceBetweenNodes(center, other))
    val mean = distances.sum / distances.size
    val std = Math.sqrt(distances.map(d => Math.pow(d - mean, 2)).sum / distances.size)
    java.util.Map.of("distance[mean]", mean, "distance[std]", std)
  }
}
