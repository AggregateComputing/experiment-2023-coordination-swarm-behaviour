package it.unibo.alchemist.loader.`export`.extractors

import it.unibo.alchemist.loader.`export`.Extractor
import it.unibo.alchemist.model.implementations.molecules.SimpleMolecule
import it.unibo.alchemist.model.interfaces
import it.unibo.alchemist.model.interfaces.{Actionable, Environment, Position2D}

import java.util
import scala.jdk.CollectionConverters.IteratorHasAsScala

class ChoicesExtractor() extends Extractor[Double] {
  override def getColumnNames: util.List[String] = util.List.of("choices")
  override def extractData[T](
                               environment: Environment[T, _],
                               actionable: Actionable[T],
                               time: interfaces.Time,
                               l: Long
                             ): util.Map[String, Double] = {
    type EnvironmentType = Environment[T, Position2D[_]]
    val typeEnvironment = environment.asInstanceOf[EnvironmentType]
    val nodes = typeEnvironment.getNodes.iterator().asScala.toList

    val choicesCount = nodes
      .map(node => node.getConcentration(new SimpleMolecule("choice")))
      .map(_.asInstanceOf[Int])
      .groupBy(identity)
      .size

    java.util.Map.of("choices", choicesCount.toDouble)
  }
}
