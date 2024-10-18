package it.unibo.alchemist.loader.`export`.extractors

import it.unibo.alchemist.boundary.Extractor
import it.unibo.alchemist.model.molecules.SimpleMolecule
import it.unibo.alchemist.model.{Actionable, Environment, Position2D, Time}

import java.util
import scala.jdk.CollectionConverters.{CollectionHasAsScala, IteratorHasAsScala}

class VerticalDeviationExtractor(val initialCenterId: Int) extends Extractor[Double] {
  override def getColumnNames: util.List[String] = util.List.of("deviation[mean]", "deviation[std]")

  override def extractData[T](
                               environment: Environment[T, _],
                               actionable: Actionable[T],
                               time: Time,
                               l: Long
                             ): util.Map[String, Double] = {
    type EnvironmentType = Environment[T, Position2D[_]]
    val typeEnvironment = environment.asInstanceOf[EnvironmentType]
    val centerId = environment.getNodes
      .iterator()
      .asScala
      .filter(node => node.getConcentration(new SimpleMolecule("lead")).asInstanceOf[Boolean])
      .collectFirst({ case node => node.getId })
      .getOrElse(initialCenterId)
    val center = typeEnvironment.getNodeByID(centerId)
    val others = typeEnvironment.getNodes.asScala.filterNot(_ == center)
    val positions = others.map(node => typeEnvironment.getPosition(node))
    val centerPosition = typeEnvironment.getPosition(center)
    val verticalError = positions.map(position => math.abs(position.getY - centerPosition.getY))
    val mean = verticalError.sum / verticalError.size
    val std = Math.sqrt(verticalError.map(d => Math.pow(d - mean, 2)).sum / verticalError.size)
    java.util.Map.of("deviation[mean]", mean, "deviation[std]", std)
  }
}
